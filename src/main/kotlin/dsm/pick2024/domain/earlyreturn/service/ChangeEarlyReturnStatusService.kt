package dsm.pick2024.domain.earlyreturn.service

import dsm.pick2024.domain.admin.port.`in`.AdminFacadeUseCase
import dsm.pick2024.domain.application.domain.Application
import dsm.pick2024.domain.application.enums.ApplicationKind
import dsm.pick2024.domain.application.enums.ApplicationType
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.port.out.DeleteApplicationPort
import dsm.pick2024.domain.application.port.out.QueryApplicationPort
import dsm.pick2024.domain.application.port.out.SaveApplicationPort
import dsm.pick2024.domain.applicationstory.domain.ApplicationStory
import dsm.pick2024.domain.applicationstory.enums.Type
import dsm.pick2024.domain.applicationstory.port.out.SaveAllApplicationStoryPort
import dsm.pick2024.domain.attendance.domain.service.AttendanceService
import dsm.pick2024.domain.attendance.port.out.QueryAttendancePort
import dsm.pick2024.domain.attendance.port.out.SaveAttendancePort
import dsm.pick2024.domain.earlyreturn.exception.EarlyReturnApplicationNotFoundException
import dsm.pick2024.domain.earlyreturn.port.`in`.ChangeEarlyReturnStatusUseCase
import dsm.pick2024.domain.earlyreturn.presentation.dto.request.StatusEarlyReturnRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class ChangeEarlyReturnStatusService(
    private val adminFacadeUseCase: AdminFacadeUseCase,
    private val saveApplicationPort: SaveApplicationPort,
    private val queryApplicationPort: QueryApplicationPort,
    private val applicationStorySaveAllPort: SaveAllApplicationStoryPort,
    private val deleteApplicationPort: DeleteApplicationPort,
    private val saveAttendancePort: SaveAttendancePort,
    private val queryAttendancePort: QueryAttendancePort,
    private val attendanceService: AttendanceService

) : ChangeEarlyReturnStatusUseCase {

    @Transactional
    override fun statusEarlyReturn(request: StatusEarlyReturnRequest) {
        val admin = adminFacadeUseCase.currentAdmin()

        if (request.status == Status.NO) {
            handleRejection(request.ids)
        } else {
            handleApproval(request.ids, admin.name)
        }
    }

    private fun handleRejection(ids: List<UUID>) {
        ids.forEach { id ->
            queryApplicationPort.findByIdAndApplicationKind(id, ApplicationKind.EARLY_RETURN)
                ?: throw EarlyReturnApplicationNotFoundException
            deleteApplicationPort.deleteByIdAndApplicationKind(id, ApplicationKind.EARLY_RETURN)
        }
    }

    private fun handleApproval(ids: List<UUID>, teacherName: String) {
        val earlyReturns = ids.mapNotNull { id ->
            queryApplicationPort.findByIdAndApplicationKind(id, ApplicationKind.EARLY_RETURN)?.copy(
                teacherName = teacherName,
                status = Status.OK
            )
        }


        val applicationStories = earlyReturns.map { earlyReturn ->
            createApplicationStory(earlyReturn)
        }

        saveApplicationPort.saveAll(earlyReturns)
        applicationStorySaveAllPort.saveAll(applicationStories)
    }

    private fun createApplicationStory(application: Application): ApplicationStory {
        val start = attendanceService.translateApplication(application.start, null, ApplicationType.TIME)
        return ApplicationStory(
            reason = application.reason,
            userName = application.userName,
            start = start.first(),
            date = application.date,
            type = Type.EARLY_RETURN,
            userId = application.userId
        )
    }
}
