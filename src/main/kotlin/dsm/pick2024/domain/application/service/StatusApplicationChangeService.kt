package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.application.enums.ApplicationStatus
import dsm.pick2024.domain.application.exception.ApplicationNotFoundException
import dsm.pick2024.domain.application.port.`in`.StatusApplicationChangeUseCase
import dsm.pick2024.domain.application.port.out.FindApplicationByIdPort
import dsm.pick2024.domain.application.port.out.SaveApplicationPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class StatusApplicationChangeService(
    private val findApplicationByIdPort: FindApplicationByIdPort,
    private val saveApplicationPort: SaveApplicationPort
) : StatusApplicationChangeUseCase {

    @Transactional
    override fun statusApplicationChange(applicationId: UUID, applicationStatus: ApplicationStatus) {
        val application = findApplicationByIdPort.findById(applicationId) ?: throw ApplicationNotFoundException

        val update = application.copy(
            applicationStatus = applicationStatus
        )
        saveApplicationPort.save(update)
    }
}
