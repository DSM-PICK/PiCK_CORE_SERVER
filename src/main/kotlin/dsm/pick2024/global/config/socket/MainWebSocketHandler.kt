package dsm.pick2024.global.config.socket

import com.fasterxml.jackson.databind.ObjectMapper
import dsm.pick2024.domain.main.port.`in`.MainUseCase
import dsm.pick2024.global.security.jwt.JwtTokenProvider
import org.springframework.stereotype.Component
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

@Component
class MainWebSocketHandler(
    private val mainUseCase: MainUseCase,
    private val jwtTokenProvider: JwtTokenProvider
) : TextWebSocketHandler() {

    private val objectMapper = ObjectMapper()
    private val sessions = HashMap<String, WebSocketSession>()

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val authorization = session.handshakeHeaders.getFirst("Authorization")
        val token = authorization!!.removePrefix("Bearer ")
        val claims = jwtTokenProvider.getClaimsToken(token)
        val userId = claims.get("sub", String::class.java)
        sessions[userId] = session
        mainUseCase.main(userId, session)
    }

    fun sendStatusUpdate(userId: String, status: Any?) {
        val session = sessions[userId]
        if (session?.isOpen == true) {
            val jsonResponse = objectMapper.writeValueAsString(status)
            session.sendMessage(TextMessage(jsonResponse))
        }
    }
}
