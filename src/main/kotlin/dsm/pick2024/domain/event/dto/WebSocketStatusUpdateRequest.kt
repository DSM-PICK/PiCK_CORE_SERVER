package dsm.pick2024.domain.event.dto

import org.springframework.context.ApplicationEvent

class WebSocketStatusUpdateRequest(
    source: Any,
    val status: Any?,
    val userId: String
) : ApplicationEvent(source)
