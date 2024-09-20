package dsm.pick2024.global.config.socket

import com.fasterxml.jackson.databind.ObjectMapper
import dsm.pick2024.domain.main.MainService
import org.springframework.stereotype.Component
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

@Component
class MainWebSocketHandler(
    private val mainService: MainService
) : TextWebSocketHandler() {

    private val objectMapper = ObjectMapper()

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val response = mainService.main(message.payload)
        val jsonResponse = objectMapper.writeValueAsString(response)
        session.sendMessage(TextMessage(jsonResponse))
    }
}
