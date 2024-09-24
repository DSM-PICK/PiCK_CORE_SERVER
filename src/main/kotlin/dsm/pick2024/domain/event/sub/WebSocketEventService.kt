package dsm.pick2024.domain.event.sub

import dsm.pick2024.domain.event.WebSocketStatusUpdateRequest
import dsm.pick2024.global.config.socket.MainWebSocketHandler
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class WebSocketEventService(
    private val webSocketHandler: MainWebSocketHandler
) {
    @EventListener(WebSocketStatusUpdateRequest::class)
    fun onWebSocketStatusUpdateEvent(event: WebSocketStatusUpdateRequest) {
        webSocketHandler.sendStatusUpdate(event.userId, event.status)
    }
}
