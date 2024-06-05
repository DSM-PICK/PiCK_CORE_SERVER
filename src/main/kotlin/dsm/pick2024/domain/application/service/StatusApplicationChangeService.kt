package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.application.exception.ApplicationNotFoundException
import dsm.pick2024.domain.application.port.`in`.StatusApplicationChangeUseCase
import dsm.pick2024.domain.application.port.out.DeleteApplicationPort
import dsm.pick2024.domain.application.port.out.QueryApplicationPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class StatusApplicationChangeService(
    private val queryApplicationPort: QueryApplicationPort,
    private val deleteApplicationPort: DeleteApplicationPort
) : StatusApplicationChangeUseCase {
    @Transactional
    override fun statusApplicationChange(applicationId: UUID) {
        queryApplicationPort.findById(applicationId)
            ?: throw ApplicationNotFoundException

        deleteApplicationPort.deleteById(applicationId)
    }
}
