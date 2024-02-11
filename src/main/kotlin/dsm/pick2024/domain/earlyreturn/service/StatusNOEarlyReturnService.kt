package dsm.pick2024.domain.earlyreturn.service

import dsm.pick2024.domain.earlyreturn.domain.EarlyReturn
import dsm.pick2024.domain.earlyreturn.exception.EarlyReturnApplicationNotFoundException
import dsm.pick2024.domain.earlyreturn.port.`in`.StatusNOEarlyReturnUseCase
import dsm.pick2024.domain.earlyreturn.port.out.DeleteAllEarlyReturnListPort
import dsm.pick2024.domain.earlyreturn.port.out.FindEarlyReturnByIdPort
import dsm.pick2024.domain.earlyreturn.presentation.dto.request.StatusEarlyReturnRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StatusNOEarlyReturnService(
    private val findEarlyReturnByIdPort: FindEarlyReturnByIdPort,
    private val deleteAllEarlyReturnPort: DeleteAllEarlyReturnListPort
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
