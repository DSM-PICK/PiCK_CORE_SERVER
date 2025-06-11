package dsm.pick2024.domain.earlyreturn.service

import dsm.pick2024.domain.admin.port.`in`.AdminFacadeUseCase
import dsm.pick2024.domain.application.domain.Application
import dsm.pick2024.domain.application.enums.ApplicationKind
import dsm.pick2024.domain.application.enums.ApplicationType
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.event.dto.ChangeStatusRequest
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
import dsm.pick2024.domain.fcm.port.`in`.FcmSendMessageUseCase
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import org.springframework.context.ApplicationEventPublisher
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
    private val attendanceService: AttendanceService,
    private val eventPublisher: ApplicationEventPublisher,
    private val fcmSendMessageUseCase: FcmSendMessageUseCase,
    private val userFacadeUseCase: UserFacadeUseCase
) : ChangeEarlyReturnStatusUseCase {

    @Transactional
    override fun statusEarlyReturn(request: StatusEarlyReturnRequest) {
        val admin = adminFacadeUseCase.currentAdmin()

        val deviceTokens = request.idList.mapNotNull {
            userFacadeUseCase.getUserByXquareId(
                findApplicationById(it).userId
            ).deviceToken
        }.filter { it.isNotBlank() }

        when (request.status) {
            Status.NO -> {
                fcmSendMessageUseCase.execute(deviceTokens, "조귀귀가 신청 반려 안내", "${admin.name} 선생님이 조귀귀가 신청을 반려하셨습니다.")
            }
            Status.OK -> {
                fcmSendMessageUseCase.execute(deviceTokens, "조귀귀가 신청 승인 안내", "${admin.name} 선생님이 조귀귀가 신청을 승인하셨습니다.")
            }
            else -> {
            }
        }

        if (request.status == Status.NO) {
            handleStatusNo(request.idList)
            return
        }

        val updateEarlyReturnList = request.idList.map { id ->
            val application = findApplicationById(id)
            updateEarlyReturn(application, admin.name)
        }

        val applicationStory = updateEarlyReturnList.map { earlyReturn ->
            createApplicationStory(earlyReturn)
        }

        val attendances = updateEarlyReturnList.map { it ->
            val attendanceId = queryAttendancePort.findByUserId(it.userId)
            attendanceService.updateAttendanceToEarlyReturn(it.start, attendanceId!!)
        }.toMutableList()

        saveApplicationPort.saveAll(updateEarlyReturnList)
        applicationStorySaveAllPort.saveAll(applicationStory)
        saveAttendancePort.saveAll(attendances)
        eventPublisher.publishEvent(ChangeStatusRequest(this, updateEarlyReturnList.map { it.userId }))
    }

    private fun handleStatusNo(idList: List<UUID>) {
        eventPublisher.publishEvent(ChangeStatusRequest(this, idList.map { findApplicationById(it).userId }))
        idList.forEach { id ->
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
        return queryApplicationPort.findById(id)
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
