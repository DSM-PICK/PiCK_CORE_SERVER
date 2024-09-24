package dsm.pick2024.domain.event

import org.springframework.context.ApplicationEvent

class WebSocketStatusUpdateRequest(
    source: Any,
    val status: Any?,
    val userId: String
) : ApplicationEvent(source)
