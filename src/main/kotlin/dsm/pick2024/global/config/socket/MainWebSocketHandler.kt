package dsm.pick2024.global.config.socket

import com.fasterxml.jackson.databind.ObjectMapper
import dsm.pick2024.domain.main.MainService
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

@Component
class MainWebSocketHandler(
    private val mainService: MainService
) : TextWebSocketHandler(), ApplicationListener<WebSocketStatusUpdateEvent> {

    private val objectMapper = ObjectMapper()

    fun sendStatusUpdate(session: WebSocketSession, status: Any?) {
        if (status != null) {
            val jsonResponse = objectMapper.writeValueAsString(status)
            session.sendMessage(TextMessage(jsonResponse))
        }
    }

    override fun onApplicationEvent(event: WebSocketStatusUpdateEvent) {
        sendStatusUpdate(event.session, event.status)
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val authorization = session.handshakeHeaders.getFirst("Authorization")
        mainService.main(session, authorization.toString())
    }
}
