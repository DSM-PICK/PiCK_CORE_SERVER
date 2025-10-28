package dsm.pick2024.domain.application.finder

import dsm.pick2024.domain.application.domain.Application
import dsm.pick2024.domain.application.enums.ApplicationKind
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.exception.ApplicationNotFoundException
import dsm.pick2024.domain.application.port.`in`.ApplicationFinderUseCase
import dsm.pick2024.domain.application.port.out.QueryApplicationPort
import org.springframework.stereotype.Component
import java.util.*

@Component
class ApplicationFinder(
    private val queryApplicationPort: QueryApplicationPort
) : ApplicationFinderUseCase {
    override fun findByIdOrThrow(id: UUID) = queryApplicationPort.findById(id) ?: throw ApplicationNotFoundException
    override fun findByUserIdOrThrow(id: UUID): Application {
        return queryApplicationPort.findByUserId(id) ?: throw ApplicationNotFoundException
    }

    override fun findByGradeAndClassNumAndApplicationKindOrThrow(
        grade: Int,
        classNum: Int,
        applicationKind: ApplicationKind
    ) = queryApplicationPort.findByGradeAndClassNumAndApplicationKind(grade, classNum, applicationKind)
        ?: throw ApplicationNotFoundException

    override fun findByFloorAndApplicationKindOrThrow(floor: Int, applicationKind: ApplicationKind): List<Application> =
        queryApplicationPort.findByFloorAndApplicationKind(floor, applicationKind) ?: throw ApplicationNotFoundException

    override fun findByUserIdAndStatusAndApplicationKindOrThrow(
        status: Status,
        id: UUID,
        applicationKind: ApplicationKind
    ) = queryApplicationPort.findByUserIdAndStatusAndApplicationKind(status, id, applicationKind)
        ?: throw ApplicationNotFoundException

    override fun queryApplicationWithAttendanceOrThrow(floor: Int) =
        queryApplicationPort.queryApplicationWithAttendance(floor) ?: throw ApplicationNotFoundException
}
