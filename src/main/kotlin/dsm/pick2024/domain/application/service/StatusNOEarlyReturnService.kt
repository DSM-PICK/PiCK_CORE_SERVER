package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.application.domain.EarlyReturn
import dsm.pick2024.domain.application.exception.EarlyReturnApplicationNotFoundException
import dsm.pick2024.domain.application.port.`in`.StatusNOEarlyReturnUseCase
import dsm.pick2024.domain.application.port.out.DeleteAllEarlyReturnPort
import dsm.pick2024.domain.application.port.out.FindEarlyReturnByIdPort
import dsm.pick2024.domain.application.presentation.dto.request.StatusEarlyReturnRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StatusNOEarlyReturnService(
    private val findEarlyReturnByIdPort: FindEarlyReturnByIdPort,
    private val deleteAllEarlyReturnPort: DeleteAllEarlyReturnPort
) : StatusNOEarlyReturnUseCase {

    @Transactional
    override fun statusNOEarlyReturn(request: StatusEarlyReturnRequest?) {
        val earlyReturnUpdate = mutableListOf<EarlyReturn>()

        for (earlyReturnId in request!!.earlyReturnIds) {
            val earlyReturn = findEarlyReturnByIdPort.findById(earlyReturnId)
                ?: throw EarlyReturnApplicationNotFoundException

            val updateEarlyReturn = earlyReturn
            earlyReturnUpdate.add(updateEarlyReturn)
        }
        deleteAllEarlyReturnPort.deleteAll(earlyReturnUpdate)
    }
}
