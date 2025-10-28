package dsm.pick2024.domain.application.port.`in`

import dsm.pick2024.domain.application.domain.Application
import dsm.pick2024.domain.application.enums.ApplicationKind
import dsm.pick2024.domain.application.enums.Status
import java.util.*

interface ApplicationFinderUseCase {
    fun findByIdOrThrow(id: UUID): Application
    fun findByUserIdOrThrow(id: UUID): Application
    fun findByGradeAndClassNumAndApplicationKindOrThrow(
        grade: Int,
        classNum: Int,
        applicationKind: ApplicationKind
    ): List<Application>

    fun findByFloorAndApplicationKindOrThrow(floor: Int, applicationKind: ApplicationKind): List<Application>

    fun findByUserIdAndStatusAndApplicationKindOrThrow(
        status: Status,
        id: UUID,
        applicationKind: ApplicationKind
    ): Application

    fun queryApplicationWithAttendanceOrThrow(floor: Int): List<Application>
}
