package dsm.pick2024.infrastructure.sse

import dsm.pick2024.infrastructure.sse.port.out.SseRegistryPort
import org.apache.catalina.connector.ClientAbortException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.io.IOException
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList

@Component
class SseRegistry : SseRegistryPort {
    private val emitters: MutableMap<UUID, MutableList<SseEmitter>> = ConcurrentHashMap()
    private val logger = LoggerFactory.getLogger(SseRegistry::class.java)

    /**
     * Creates and registers a Server-Sent Events emitter associated with the given user.
     *
     * @param userId The UUID of the user to associate the emitter with.
     * @return The registered `SseEmitter` instance for the user.
     */
    override fun add(userId: UUID): SseEmitter {
        val emitter = SseEmitter(60 * 60 * 1000L * 2) // 2시간
        emitters.computeIfAbsent(userId) { CopyOnWriteArrayList() }.add(emitter)

        registerEmitterCallbacks(emitter, userId)

        return emitter
    }

    override fun remove(userId: UUID, emitter: SseEmitter) {
        val list = emitters[userId] ?: return
        list.remove(emitter)
        if (list.isEmpty()) emitters.remove(userId)
    }

    /**
     * Sends an SSE event with the given data to all active emitters for the specified user.
     *
     * If `data` is `null`, a single dot ("." ) is sent as the payload. Emitters that fail
     * to send (including client aborts) are completed with the error and removed from the registry.
     *
     * @param userId The UUID of the target user whose emitters should receive the event.
     * @param data The event payload to send; if `null`, a "." placeholder is sent.
     */
    override fun sendToUser(userId: UUID, data: Any?) {
        val list = emitters[userId] ?: return
        for (emitter in list) {
            try {
                emitter.send(SseEmitter.event().data(data ?: "."))
            } catch (e: Exception) {
                if (isClientAbort(e)) {
                    emitter.completeWithError(e)
                    remove(userId, emitter)
                    continue
                }
                logger.error("User Event Send Failed ${e.message}", e)
                emitter.completeWithError(e)
                remove(userId, emitter)
            }
        }
    }

    /**
     * Broadcasts a heartbeat event to all registered users.
     *
     * Iterates over the current registry of user IDs and sends a `"heartbeat"` event to each user's emitters.
     * Emitters that fail during send (for example due to client disconnects) may be completed and removed from the registry as part of send handling.
     */
    override fun sendHeartbeat() {
        for (userId in emitters.keys) {
            sendToUser(userId, "heartbeat")
        }
    }

    /**
     * Determines whether an exception represents a client-side abort (for example, a broken pipe).
     *
     * @param e The exception to inspect.
     * @return `true` if the exception is a `ClientAbortException` or its message contains "Broken pipe" (case-insensitive), `false` otherwise.
     */
    private fun isClientAbort(e: Exception): Boolean {
        if (e is ClientAbortException) return true
        val io = e as? IOException
        val message = (io?.message ?: e.message).orEmpty()
        return message.contains("Broken pipe", ignoreCase = true)
    }

    /**
     * Registers lifecycle callbacks on the given SSE emitter to remove it from the registry when it completes, times out, or errors.
     *
     * @param emitter The SseEmitter to attach callbacks to.
     * @param userId The owner user's UUID whose emitter list will be cleaned up when the emitter ends.
     */
    private fun registerEmitterCallbacks(emitter: SseEmitter, userId: UUID) {
        val cleanup = Runnable { remove(userId, emitter) }

        emitter.onCompletion(cleanup)
        emitter.onTimeout {
            emitter.complete()
            cleanup.run()
        }
        emitter.onError {
            emitter.complete()
            cleanup.run()
        }
    }
}