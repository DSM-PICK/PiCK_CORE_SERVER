package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.admin.port.`in`.AdminFacadeUseCase
import dsm.pick2024.domain.application.domain.Application
import dsm.pick2024.domain.application.enums.ApplicationKind
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.event.dto.ChangeStatusRequest
import dsm.pick2024.domain.application.port.`in`.ApplicationFinderUseCase
import dsm.pick2024.domain.application.port.`in`.ChangeApplicationStatusUseCase
import dsm.pick2024.domain.application.port.out.DeleteApplicationPort
import dsm.pick2024.domain.application.port.out.QueryApplicationPort
import dsm.pick2024.domain.application.port.out.SaveApplicationPort
import dsm.pick2024.domain.application.presentation.dto.request.ApplicationStatusRequest
import dsm.pick2024.domain.applicationstory.domain.ApplicationStory
import dsm.pick2024.domain.applicationstory.enums.Type
import dsm.pick2024.domain.applicationstory.port.out.SaveAllApplicationStoryPort
import dsm.pick2024.domain.attendance.domain.service.AttendanceService
import dsm.pick2024.domain.attendance.port.out.QueryAttendancePort
import dsm.pick2024.domain.attendance.port.out.SaveAttendancePort
import dsm.pick2024.domain.event.enums.EventTopic
import dsm.pick2024.domain.fcm.port.`in`.FcmSendMessageUseCase
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class ChangeApplicationStatusService(
    private val adminFacadeUseCase: AdminFacadeUseCase,
    private val queryApplicationPort: QueryApplicationPort,
    private val saveApplicationPort: SaveApplicationPort,
    private val applicationStorySaveAllPort: SaveAllApplicationStoryPort,
    private val deleteApplicationPort: DeleteApplicationPort,
    private val saveAttendancePort: SaveAttendancePort,
    private val queryAttendancePort: QueryAttendancePort,
    private val attendanceService: AttendanceService,
    private val eventPublisher: ApplicationEventPublisher,
    private val sendMessageUseCase: FcmSendMessageUseCase,
    private val userFacadeUseCase: UserFacadeUseCase,
    private val applicationFinderUseCase: ApplicationFinderUseCase
) : ChangeApplicationStatusUseCase {

    @Transactional
    override fun changeStatusApplication(request: ApplicationStatusRequest) {
        val admin = adminFacadeUseCase.currentAdmin()
        val applications = request.idList.map { findApplicationById(it) }
        val deviceTokens = applications.mapNotNull {
            userFacadeUseCase.getUserByXquareId(
                it.userId
            ).deviceToken
        }.filter { it.isNotBlank() }

        when (request.status) {
            Status.NO -> {
                sendMessageUseCase.execute(deviceTokens, "외출 신청 반려 안내", "${admin.name} 선생님이 외출 신청을 반려하셨습니다.")
            }
            Status.OK -> {
                sendMessageUseCase.execute(deviceTokens, "외출 신청 승인 안내", "${admin.name} 선생님이 외출 신청을 승인하셨습니다.")
            }
            else -> {
            }
        }

        if (request.status == Status.NO) {
            handleStatusNo(request.idList)
        } else {
            val updateApplicationList = applications.map {
                updateApplication(it, admin.name)
            }

            val applicationStory = updateApplicationList.map { it ->
                createApplicationStory(it)
            }

            val attendance = updateApplicationList.map { it ->
                val attendanceId = queryAttendancePort.findByUserId(it.userId)
                attendanceService.updateAttendanceToApplication(it.start, it.end!!, it.applicationType, attendanceId!!)
            }.toMutableList()

            saveApplicationPort.saveAll(updateApplicationList)
            applicationStorySaveAllPort.saveAll(applicationStory)
            saveAttendancePort.saveAll(attendance)
            eventPublisher.publishEvent(
                ChangeStatusRequest(EventTopic.HANDLE_EVENT, updateApplicationList.map { it.userId })
            )
        }
    }

    private fun handleStatusNo(idList: List<UUID>) {
        val applicationList = idList.map { findApplicationById(it) }
        eventPublisher.publishEvent(ChangeStatusRequest(this, applicationList.map { it.userId }))
        idList.map { id ->
            val application = findApplicationById(id)
            deleteApplicationPort.deleteByIdAndApplicationKind(application.id!!, ApplicationKind.APPLICATION)
        }
    }

    private fun findApplicationById(id: UUID): Application {
        return applicationFinderUseCase.findByIdOrThrow(id)
    }

    private fun updateApplication(application: Application, adminName: String): Application {
        return application.copy(
            teacherName = adminName,
            status = Status.OK
        )
    }

    private fun createApplicationStory(application: Application): ApplicationStory {
        val startAndEnd = attendanceService.translateApplication(
            application.start,
            application.end!!,
            application.applicationType
        )
        return ApplicationStory(
            reason = application.reason,
            userName = application.userName,
            start = startAndEnd.first(),
            end = startAndEnd.last(),
            date = application.date,
            type = Type.APPLICATION,
            userId = application.userId
        )
    }
}
