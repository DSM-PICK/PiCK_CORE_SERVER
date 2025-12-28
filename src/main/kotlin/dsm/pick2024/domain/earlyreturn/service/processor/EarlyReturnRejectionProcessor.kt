package dsm.pick2024.domain.earlyreturn.service.processor

import dsm.pick2024.domain.application.domain.Application
import dsm.pick2024.domain.application.enums.ApplicationKind
import dsm.pick2024.domain.application.port.out.DeleteApplicationPort
import dsm.pick2024.domain.event.dto.ChangeStatusRequest
import dsm.pick2024.domain.outbox.port.`in`.OutboxFacadeUseCase
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class EarlyReturnRejectionProcessor(
    outboxFacadeUseCase: OutboxFacadeUseCase,
    private val eventPublisher: ApplicationEventPublisher,
    private val deleteApplicationPort: DeleteApplicationPort

) : EarlyReturnStatusProcessor(outboxFacadeUseCase) {
    override fun process(applications: List<Application>, adminName: String, deviceTokens: List<String>) {
        applications.map {
            deleteApplicationPort.deleteByIdAndApplicationKind(it.id, ApplicationKind.EARLY_RETURN)
        }

        sendNotification("조기귀가 신청 반려 안내", "$adminName 선생님께서 조기귀가 신청을 반려하였습니다. ", deviceTokens)

        eventPublisher.publishEvent(ChangeStatusRequest(this, applications.map { it.userId }))
    }
}
