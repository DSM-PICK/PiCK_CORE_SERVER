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

    override fun sendToUser(userId: UUID, data: Any?) {
        val list = emitters[userId] ?: return
        for (emitter in list) {
            try {
                emitter.send(SseEmitter.event().data(data ?: "."))
            } catch (e: Exception) {
                // send 실패 시 컨테이너가 onError로 정리하므로 completeWithError를 호출하지 않는다
                // (끊긴 연결에 completeWithError → error dispatch가 Tomcat NPE를 유발)
                if (!isClientAbort(e)) {
                    logger.error("User Event Send Failed ${e.message}", e)
                }
                remove(userId, emitter)
            }
        }
    }

    override fun sendHeartbeat() {
        for (userId in emitters.keys) {
            sendToUser(userId, "heartbeat")
        }
    }

    // Broken pipe error인지 확인
    private fun isClientAbort(e: Exception): Boolean {
        if (e is ClientAbortException) return true
        val io = e as? IOException
        val message = (io?.message ?: e.message).orEmpty()
        return message.contains("Broken pipe", ignoreCase = true)
    }

    private fun registerEmitterCallbacks(emitter: SseEmitter, userId: UUID) {
        val cleanup = Runnable { remove(userId, emitter) }

        emitter.onCompletion(cleanup)
        emitter.onTimeout {
            emitter.complete()
            cleanup.run()
        }
        emitter.onError {
            cleanup.run()
        }
    }
}
