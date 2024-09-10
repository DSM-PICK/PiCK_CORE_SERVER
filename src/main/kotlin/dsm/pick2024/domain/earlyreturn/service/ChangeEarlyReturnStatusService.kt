package dsm.pick2024.domain.earlyreturn.service

import dsm.pick2024.domain.admin.port.`in`.AdminFacadeUseCase
import dsm.pick2024.domain.application.domain.Application
import dsm.pick2024.domain.application.enums.ApplicationKind
import dsm.pick2024.domain.application.enums.ApplicationType
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.exception.ApplicationNotFoundException
import dsm.pick2024.domain.application.port.out.DeleteApplicationPort
import dsm.pick2024.domain.application.port.out.QueryApplicationPort
import dsm.pick2024.domain.application.port.out.SaveApplicationPort
import dsm.pick2024.domain.applicationstory.domain.ApplicationStory
import dsm.pick2024.domain.applicationstory.enums.Type
import dsm.pick2024.domain.applicationstory.port.out.SaveAllApplicationStoryPort
import dsm.pick2024.domain.attendance.domain.service.AttendanceService
import dsm.pick2024.domain.attendance.port.out.QueryAttendancePort
import dsm.pick2024.domain.attendance.port.out.SaveAttendancePort
import dsm.pick2024.domain.earlyreturn.port.`in`.ChangeEarlyReturnStatusUseCase
import dsm.pick2024.domain.earlyreturn.presentation.dto.request.StatusEarlyReturnRequest
import dsm.pick2024.domain.event.Topic
import dsm.pick2024.domain.event.application.SendMessageToApplicationEventPort
import dsm.pick2024.domain.user.port.out.QueryUserPort
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
    private val queryUserPort: QueryUserPort,
    private val sendMessageToApplicationEventPort: SendMessageToApplicationEventPort,
    private val attendanceService: AttendanceService

) : ChangeEarlyReturnStatusUseCase {

    @Transactional
    override fun statusEarlyReturn(request: StatusEarlyReturnRequest) {
        val admin = adminFacadeUseCase.currentAdmin()

        if (request.status == Status.NO) {
            handleStatusNo(request.idList)
        } else {
            handleStatusOk(request.idList, admin.name)
        }
    }

    private fun handleStatusOk(ids: List<UUID>, adminName: String) {
        val updateEarlyReturnList = ids.map { id ->
            val application = findApplicationById(id)
            val user = queryUserPort.findByXquareId(application.userId)!!
            sendMessageToApplicationEventPort.send(
                user.deviceToken!!, Topic.APPLICATION,
                Status.OK, ApplicationKind.EARLY_RETURN,
                application
            )
            updateEarlyReturn(application, adminName)
        }

        val applicationStoryList = updateEarlyReturnList.map { earlyReturn ->
            createApplicationStory(earlyReturn)
        }

        val attendanceList = updateEarlyReturnList.map { it ->
            val attendanceId = queryAttendancePort.findByUserId(it.userId)
            attendanceService.updateAttendanceToEarlyReturn(it.start, attendanceId!!)
        }.toMutableList()

        saveApplicationPort.saveAll(updateEarlyReturnList)
        applicationStorySaveAllPort.saveAll(applicationStoryList)
        saveAttendancePort.saveAll(attendanceList)
    }

    private fun handleStatusNo(ids: List<UUID>) {
        ids.forEach { id ->
            val application = findApplicationById(id)
            val user = queryUserPort.findByXquareId(application.userId)!!
            sendMessageToApplicationEventPort.send(
                user.deviceToken!!, Topic.APPLICATION,
                Status.NO, ApplicationKind.EARLY_RETURN,
                null
            )
            deleteApplicationPort.deleteByIdAndApplicationKind(id, ApplicationKind.EARLY_RETURN)
        }
    }

    private fun updateEarlyReturn(application: Application, adminName: String): Application {
        return application.copy(
            teacherName = adminName,
            status = Status.OK
        )
    }

    private fun findApplicationById(id: UUID): Application {
        return queryApplicationPort.findByIdAndApplicationKind(id, ApplicationKind.EARLY_RETURN)
            ?: throw ApplicationNotFoundException
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
