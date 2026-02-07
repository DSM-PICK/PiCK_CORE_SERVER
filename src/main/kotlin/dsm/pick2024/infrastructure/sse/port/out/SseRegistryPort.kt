package dsm.pick2024.infrastructure.sse.port.out

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.*

interface SseRegistryPort {
    fun sendToUser(userId: UUID, data: Any?)
    fun remove(userId: UUID, emitter: SseEmitter)
    fun add(userId: UUID): SseEmitter
    fun sendHeartbeat()
}
