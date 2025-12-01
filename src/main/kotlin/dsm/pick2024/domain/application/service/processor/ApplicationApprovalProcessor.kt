package dsm.pick2024.domain.application.service.processor

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
import dsm.pick2024.domain.event.enums.EventTopic
import dsm.pick2024.domain.fcm.port.out.FcmSendPort
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class ApplicationApprovalProcessor(
    private val saveApplicationPort: SaveApplicationPort,
    private val applicationStorySaveAllPort: SaveAllApplicationStoryPort,
    private val attendanceFinderUseCase: AttendanceFinderUseCase,
    private val saveAttendancePort: SaveAttendancePort,
    private val attendanceService: AttendanceService,
    private val eventPublisher: ApplicationEventPublisher,
    sendMessageUseCase: FcmSendPort
) : ApplicationStatusProcessor(sendMessageUseCase) {

    override fun process(applications: List<Application>, adminName: String, deviceTokens: List<String>) {
        val updateApplications = applications.map { it.copy(teacherName = adminName, status = Status.OK) }

        val applicationStories = updateApplications.map {
            val (start, end) = attendanceService.translateApplication(it.start, it.end!!, it.applicationType)
            ApplicationStory(
                reason = it.reason,
                userName = it.userName,
                start = start,
                end = end,
                date = it.date,
                type = Type.APPLICATION,
                userId = it.userId
            )
        }

        val attendances = updateApplications.map {
            val attendance = attendanceFinderUseCase.findByUserIdOrThrow(it.userId)
            attendanceService.updateAttendanceToApplication(it.start, it.end!!, it.applicationType, attendance)
        }

        saveApplicationPort.saveAll(updateApplications)
        applicationStorySaveAllPort.saveAll(applicationStories)
        saveAttendancePort.saveAll(attendances)

        sendNotification(
            title = "외출 신청 승인 안내",
            message = "$adminName 선생님이 외출 신청을 승인하셨습니다.",
            deviceTokens = deviceTokens
        )

        eventPublisher.publishEvent(
            ChangeStatusRequest(EventTopic.HANDLE_EVENT, updateApplications.map { it.userId })
        )
    }
}
