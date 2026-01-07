package dsm.pick2024.domain.earlyreturn.service.processor

import dsm.pick2024.domain.application.domain.Application
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.port.out.SaveApplicationPort
import dsm.pick2024.domain.applicationstory.domain.ApplicationStory
import dsm.pick2024.domain.applicationstory.enums.Type
import dsm.pick2024.domain.applicationstory.port.out.SaveAllApplicationStoryPort
import dsm.pick2024.domain.attendance.domain.service.AttendanceService
import dsm.pick2024.domain.attendance.port.`in`.AttendanceFinderUseCase
import dsm.pick2024.domain.attendance.port.out.SaveAttendancePort
import dsm.pick2024.domain.event.dto.ChangeStatusRequest
import dsm.pick2024.domain.outbox.port.`in`.OutboxFacadeUseCase
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EarlyReturnApprovalProcessor(
    private val saveApplicationPort: SaveApplicationPort,
    private val applicationStorySaveAllPort: SaveAllApplicationStoryPort,
    private val saveAttendancePort: SaveAttendancePort,
    private val attendanceFinderUseCase: AttendanceFinderUseCase,
    private val attendanceService: AttendanceService,
    private val eventPublisher: ApplicationEventPublisher,
    private val outboxFacadeUseCase: OutboxFacadeUseCase
) : EarlyReturnStatusProcessor(outboxFacadeUseCase) {
    @Transactional
    override fun process(applications: List<Application>, adminName: String, deviceTokens: List<String>) {
        val updateEarlyReturnList = applications.map { it.copy(teacherName = adminName, status = Status.OK) }

        val applicationStory = updateEarlyReturnList.map { earlyReturn ->
            createApplicationStory(earlyReturn)
        }

        sendNotification("조기귀가 신청 승인 안내", "$adminName 선생님이 조기귀가 신청을 승인하셨습니다.", deviceTokens)

        val attendances = updateEarlyReturnList.map {
            val attendanceId = attendanceFinderUseCase.findByUserIdOrThrow(it.userId)
            attendanceService.updateAttendanceToEarlyReturn(it.start, it.applicationType, attendanceId)
        }.toMutableList()

        saveApplicationPort.saveAll(updateEarlyReturnList)
        applicationStorySaveAllPort.saveAll(applicationStory)
        saveAttendancePort.saveAll(attendances)
        eventPublisher.publishEvent(ChangeStatusRequest(this, updateEarlyReturnList.map { it.userId }))
    }

    private fun createApplicationStory(application: Application): ApplicationStory {
        val start = attendanceService.translateEarlyReturn(application.start, application.applicationType)
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
