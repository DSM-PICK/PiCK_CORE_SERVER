package dsm.pick2024.domain.application.event

import org.springframework.context.ApplicationEvent
import java.util.*

class ApplicationStatusChangeEvent(
    source: Any,
    val userIdList: List<UUID>
) : ApplicationEvent(source)
