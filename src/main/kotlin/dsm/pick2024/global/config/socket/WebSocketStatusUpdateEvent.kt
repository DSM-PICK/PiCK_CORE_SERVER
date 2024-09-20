package dsm.pick2024.global.config.socket

import org.springframework.context.ApplicationEvent
import org.springframework.web.socket.WebSocketSession

class WebSocketStatusUpdateEvent(
    source: Any,
    val session: WebSocketSession,
    val status: Any?
) : ApplicationEvent(source)
