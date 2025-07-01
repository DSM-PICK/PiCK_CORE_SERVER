package dsm.pick2024.domain.earlyreturn.service.processor

import dsm.pick2024.domain.admin.port.`in`.AdminFacadeUseCase
import dsm.pick2024.domain.application.domain.Application
import dsm.pick2024.domain.application.enums.ApplicationType
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.port.`in`.ApplicationFinderUseCase
import dsm.pick2024.domain.application.port.out.DeleteApplicationPort
import dsm.pick2024.domain.application.port.out.QueryApplicationPort
import dsm.pick2024.domain.application.port.out.SaveApplicationPort
import dsm.pick2024.domain.applicationstory.domain.ApplicationStory
import dsm.pick2024.domain.applicationstory.enums.Type
import dsm.pick2024.domain.applicationstory.port.out.SaveAllApplicationStoryPort
import dsm.pick2024.domain.attendance.domain.service.AttendanceService
import dsm.pick2024.domain.attendance.port.out.QueryAttendancePort
import dsm.pick2024.domain.attendance.port.out.SaveAttendancePort
import dsm.pick2024.domain.event.dto.ChangeStatusRequest
import dsm.pick2024.domain.fcm.port.`in`.FcmSendMessageUseCase
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.util.*

@Service
class EarlyReturnApprovalProcessor(
    private val saveApplicationPort: SaveApplicationPort,
    private val applicationStorySaveAllPort: SaveAllApplicationStoryPort,
    private val saveAttendancePort: SaveAttendancePort,
    private val queryAttendancePort: QueryAttendancePort,
    private val attendanceService: AttendanceService,
    private val eventPublisher: ApplicationEventPublisher,
    private val sendMessageUseCase: FcmSendMessageUseCase,
    ):EarlyReturnStatusProcessor(sendMessageUseCase) {
    override fun process(applications: List<Application>, adminName: String, deviceTokens: List<String>) {
        val updateEarlyReturnList = applications.map {it.copy(teacherName = adminName, status = Status.OK)}

        val applicationStory = updateEarlyReturnList.map { earlyReturn ->
            createApplicationStory(earlyReturn)
        }

        sendNotification("조기귀가 신청 승인 안내", "$adminName 선생님이 조기귀가 신청을 승인하셨습니다.", deviceTokens)

        val attendances = updateEarlyReturnList.map {
            val attendanceId = queryAttendancePort.findByUserId(it.userId)
            attendanceService.updateAttendanceToEarlyReturn(it.start, attendanceId!!)
        }.toMutableList()

        saveApplicationPort.saveAll(updateEarlyReturnList)
        applicationStorySaveAllPort.saveAll(applicationStory)
        saveAttendancePort.saveAll(attendances)
        eventPublisher.publishEvent(ChangeStatusRequest(this, updateEarlyReturnList.map { it.userId }))
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
