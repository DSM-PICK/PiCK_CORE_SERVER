package dsm.pick2024.domain.application.event

import org.springframework.context.ApplicationEvent
import java.util.UUID

class CreateApplicationEvent(
    source: Any,
    val userId: UUID
) : ApplicationEvent(source)
