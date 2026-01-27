package dsm.pick2024.infrastructure.sse.port.out

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.*

interface SseRegistryPort {
    /**
 * Sends the given payload to the Server-Sent Events connection for the specified user.
 *
 * @param userId The UUID of the target user.
 * @param data The payload to send to the user's SSE emitter; may be `null`.
 */
fun sendToUser(userId: UUID, data: Any?)
    /**
 * Removes the specified SSE emitter associated with the given user.
 *
 * @param userId The UUID of the user whose emitter should be removed.
 * @param emitter The SseEmitter instance to remove.
 */
fun remove(userId: UUID, emitter: SseEmitter)
    /**
 * Registers and returns an SSE emitter associated with the given user ID.
 *
 * @param userId The UUID of the user to associate with the emitter.
 * @return The registered SseEmitter for the specified user.
 */
fun add(userId: UUID): SseEmitter
    /**
 * Sends a lightweight heartbeat to all registered SSE connections to keep them active.
 *
 * Implementations should emit a minimal heartbeat event to each active emitter so intermediate
 * proxies and clients do not close idle connections due to inactivity.
 */
fun sendHeartbeat()
}