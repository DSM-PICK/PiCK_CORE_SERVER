package dsm.pick2024.domain.classroom.event

import org.springframework.context.ApplicationEvent
import java.util.UUID

class BackUserClassroomEvent(
    source: Any,
    val userId: UUID
) : ApplicationEvent(source)
