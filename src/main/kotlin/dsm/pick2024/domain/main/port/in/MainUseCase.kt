package dsm.pick2024.domain.main.port.`in`

import org.springframework.web.socket.WebSocketSession
import java.util.UUID

interface MainUseCase {
    fun main(userId: String, session: WebSocketSession)

    fun onHandleEvent(userId: UUID): Any?
}
