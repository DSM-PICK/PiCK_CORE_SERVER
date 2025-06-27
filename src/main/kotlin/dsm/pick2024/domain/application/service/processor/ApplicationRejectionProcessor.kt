package dsm.pick2024.domain.application.service.processor

import dsm.pick2024.domain.application.domain.Application
import dsm.pick2024.domain.application.enums.ApplicationKind
import dsm.pick2024.domain.application.port.out.DeleteApplicationPort
import dsm.pick2024.domain.event.dto.ChangeStatusRequest
import dsm.pick2024.domain.fcm.port.`in`.FcmSendMessageUseCase
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class ApplicationRejectionProcessor(
    private val deleteApplicationPort: DeleteApplicationPort,
    private val eventPublisher: ApplicationEventPublisher,
    sendMessageUseCase: FcmSendMessageUseCase
) : ApplicationStatusProcessor(sendMessageUseCase) {

    override fun process(applications: List<Application>, adminName: String, deviceTokens: List<String>) {
        applications.forEach {
            deleteApplicationPort.deleteByIdAndApplicationKind(it.id!!, ApplicationKind.APPLICATION)
        }

        sendNotification(
            title = "외출 신청 반려 안내",
            message = "$adminName 선생님이 외출 신청을 반려하셨습니다.",
            deviceTokens = deviceTokens
        )

        eventPublisher.publishEvent(ChangeStatusRequest(this, applications.map { it.userId }))
    }
}
