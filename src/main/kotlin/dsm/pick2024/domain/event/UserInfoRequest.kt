package dsm.pick2024.domain.event

import org.springframework.context.ApplicationEvent
import java.util.*

class UserInfoRequest (
    source: Any,
    val userId: UUID
) : ApplicationEvent(source)
