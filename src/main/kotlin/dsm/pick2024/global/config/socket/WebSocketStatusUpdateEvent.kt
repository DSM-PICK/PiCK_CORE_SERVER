package dsm.pick2024.global.config.socket

import org.springframework.context.ApplicationEvent

class WebSocketStatusUpdateEvent(
    source: Any,
    val status: Any?,
    val userId: String
) : ApplicationEvent(source)
