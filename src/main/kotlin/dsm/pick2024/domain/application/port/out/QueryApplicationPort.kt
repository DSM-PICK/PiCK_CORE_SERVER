package dsm.pick2024.domain.application.port.out

import dsm.pick2024.domain.application.domain.Application
import dsm.pick2024.domain.application.enums.ApplicationKind
import java.util.*

interface QueryApplicationPort {

    fun findByIdAndApplicationKind(id: UUID, applicationKind: ApplicationKind): Application?

    fun findByGradeAndClassNumAndApplicationKind(
        grade: Int,
        classNum: Int,
        applicationKind: ApplicationKind
    ): List<Application>

    fun findByFloorAndApplicationKind(floor: Int, applicationKind: ApplicationKind): List<Application>

    fun findByUserIdAndStatusAndApplicationKind(id: UUID, applicationKind: ApplicationKind): Application?

    fun queryApplicationWithAttendance(floor: Int): List<Application>
}
