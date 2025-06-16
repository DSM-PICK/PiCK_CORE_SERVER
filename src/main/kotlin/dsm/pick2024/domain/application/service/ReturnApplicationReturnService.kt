package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.application.enums.ApplicationKind
import dsm.pick2024.domain.application.exception.ApplicationNotFoundException
import dsm.pick2024.domain.application.port.`in`.ReturnApplicationStatusUseCase
import dsm.pick2024.domain.application.port.out.DeleteApplicationPort
import dsm.pick2024.domain.application.port.out.QueryApplicationPort
import dsm.pick2024.domain.event.dto.UserInfoRequest
import dsm.pick2024.domain.event.enums.EventTopic
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
            val application = queryApplicationPort.findById(it)

            deleteApplicationPort.deleteByIdAndApplicationKind(application.id!!, ApplicationKind.APPLICATION)
            eventPublisher.publishEvent(UserInfoRequest(EventTopic.UPDATE_RETURN_TEACHER, application.userId))
        }
    }
}
