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
import dsm.pick2024.domain.application.service.processor.ApplicationApprovalProcessor
import dsm.pick2024.domain.application.service.processor.ApplicationRejectionProcessor
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
    private val userFacadeUseCase: UserFacadeUseCase,
    private val applicationFinderUseCase: ApplicationFinderUseCase,
    private val approvalProcessor: ApplicationApprovalProcessor,
    private val rejectionProcessor: ApplicationRejectionProcessor
) : ChangeApplicationStatusUseCase {

    @Transactional
    override fun changeStatusApplication(request: ApplicationStatusRequest) {
        val admin = adminFacadeUseCase.currentAdmin()
        val applications = request.idList.map { applicationFinderUseCase.findByIdOrThrow(it) }

        val deviceTokens = applications.mapNotNull {
            userFacadeUseCase.getUserByXquareId(it.userId).deviceToken
        }.filter { it.isNotBlank() }

        if (request.status == Status.OK) {
            approvalProcessor.process(applications, admin.name, deviceTokens)
        } else {
            rejectionProcessor.process(applications, admin.name, deviceTokens)
        }
    }
}
