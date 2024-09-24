package dsm.pick2024.domain.event.sub

import dsm.pick2024.domain.event.WebSocketStatusUpdateRequest
import dsm.pick2024.global.config.socket.port.out.WebSocketHandler
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class WebSocketEventService(
    private val webSocketHandler: WebSocketHandler
) {
    @EventListener(WebSocketStatusUpdateRequest::class)
    fun onWebSocketStatusUpdateEvent(event: WebSocketStatusUpdateRequest) {
        webSocketHandler.sendStatusUpdate(event.userId, event.status)
    }
}
