package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.application.enums.ApplicationKind
import dsm.pick2024.domain.application.exception.ApplicationNotFoundException
import dsm.pick2024.domain.application.port.`in`.ReturnApplicationStatusUseCase
import dsm.pick2024.domain.application.port.out.DeleteApplicationPort
import dsm.pick2024.domain.application.port.out.QueryApplicationPort
import dsm.pick2024.domain.event.UserInfoRequest
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class ReturnApplicationReturnService(
    private val queryApplicationPort: QueryApplicationPort,
    private val deleteApplicationPort: DeleteApplicationPort,
    private val eventPublisher: ApplicationEventPublisher
) : ReturnApplicationStatusUseCase {
    @Transactional
    override fun returnApplicationStatus(applicationId: List<UUID>) {
        applicationId.map {
            queryApplicationPort.findByIdAndApplicationKind(it, ApplicationKind.APPLICATION)
                ?: throw ApplicationNotFoundException

            deleteApplicationPort.deleteByIdAndApplicationKind(it, ApplicationKind.APPLICATION)
            eventPublisher.publishEvent(UserInfoRequest(this, it))
        }
    }
}
