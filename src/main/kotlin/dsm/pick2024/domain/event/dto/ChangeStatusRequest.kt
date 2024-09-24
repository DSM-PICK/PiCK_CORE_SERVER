package dsm.pick2024.domain.event.dto

import org.springframework.context.ApplicationEvent
import java.util.*

class ChangeStatusRequest(
    source: Any,
    val userIdList: List<UUID>
) : ApplicationEvent(source)
