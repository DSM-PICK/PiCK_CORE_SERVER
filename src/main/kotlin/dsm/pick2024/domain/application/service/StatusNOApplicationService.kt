package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.application.domain.Application
import dsm.pick2024.domain.application.exception.ApplicationNotFoundException
import dsm.pick2024.domain.application.port.`in`.StatusNOApplicationUseCase
import dsm.pick2024.domain.application.port.out.DeleteAllApplicationListPort
import dsm.pick2024.domain.application.port.out.FindApplicationByIdPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class StatusNOApplicationService(
    private val findByApplicationByIdPort: FindApplicationByIdPort,
    private val deleteAllApplicationPort: DeleteAllApplicationListPort
) : StatusNOApplicationUseCase {

    @Transactional
    override fun statusNOApplication(ids: List<UUID>) {
        val applicationUpdate = mutableListOf<Application>()

        for (id in ids) {
            val application = findByApplicationByIdPort.findById(id) ?: throw ApplicationNotFoundException

            applicationUpdate.add(application)
        }
        deleteAllApplicationPort.deleteAll(applicationUpdate)
    }
}
