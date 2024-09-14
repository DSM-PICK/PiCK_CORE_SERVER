package dsm.pick2024.domain.event.classroom

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.classroom.domain.Classroom

interface SendMessageToClassroomEventPot {
    fun send(
        deviceToken: String,
        status: Status,
        classroom: Classroom?,
        isSubscribed: Boolean
    )
}
