package dsm.pick2024.domain.event.classroom

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.classroom.domain.Classroom
import dsm.pick2024.domain.event.Topic

interface SendMessageToClassroomEventPot {
    fun send(
        deviceToken: String,
        topic: Topic,
        status: Status,
        classroom: Classroom?
    )
}
