package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.application.domain.Application
import dsm.pick2024.domain.application.exception.ApplicationNotFoundException
import dsm.pick2024.domain.application.port.`in`.StatusNOApplicationUseCase
import dsm.pick2024.domain.application.port.out.DeleteAllApplicationListPort
import dsm.pick2024.domain.application.port.out.FindApplicationByIdPort
import dsm.pick2024.domain.application.presentation.dto.request.StatusApplicationRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StatusNOApplicationService(
    private val findByApplicationByIdPort: FindApplicationByIdPort,
    private val deleteAllApplicationPort: DeleteAllApplicationListPort
) : StatusNOApplicationUseCase {

    @Transactional
    override fun statusNOApplication(request: StatusApplicationRequest?) {
        val applicationUpdate = mutableListOf<Application>()

        for (id in request!!.ids) {
            val application = findByApplicationByIdPort.findById(id) ?: throw ApplicationNotFoundException

            val updatedApplication = application
            applicationUpdate.add(updatedApplication)
        }
        deleteAllApplicationPort.deleteAll(applicationUpdate)
    }
}
