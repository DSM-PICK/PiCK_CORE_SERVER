package dsm.pick2024.infrastructure.sse

import dsm.pick2024.infrastructure.sse.port.out.SseRegistryPort
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
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

        val cleanup = Runnable { remove(userId, emitter) }
        emitter.onCompletion(cleanup)
        emitter.onTimeout(cleanup)
        emitter.onError { cleanup.run() }

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
                logger.error("User Event Send Failed ${e.message}", e)
                remove(userId, emitter)
            }
        }
    }
}
