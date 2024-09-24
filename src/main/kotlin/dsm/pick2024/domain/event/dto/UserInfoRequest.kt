package dsm.pick2024.domain.event.dto

import org.springframework.context.ApplicationEvent
import java.util.*

class UserInfoRequest(
    source: Any,
    val userId: UUID
) : ApplicationEvent(source)
