package dsm.pick2024.domain.application.port.out

import dsm.pick2024.domain.application.domain.Application
import dsm.pick2024.domain.application.enums.ApplicationKind
import dsm.pick2024.domain.application.enums.Status
import java.util.*

interface QueryApplicationPort {
    fun findByUserId(userId: UUID): Application?

    fun findByIdAndApplicationKind(id: UUID, applicationKind: ApplicationKind): Application?

    fun findByGradeAndClassNumAndApplicationKind(
        grade: Int,
        classNum: Int,
        applicationKind: ApplicationKind
    ): List<Application>

    fun findByFloorAndApplicationKind(floor: Int, applicationKind: ApplicationKind): List<Application>

    fun findByUserIdAndStatusAndApplicationKind(status: Status, id: UUID, applicationKind: ApplicationKind): Application?

    fun queryApplicationWithAttendance(floor: Int): List<Application>
}
