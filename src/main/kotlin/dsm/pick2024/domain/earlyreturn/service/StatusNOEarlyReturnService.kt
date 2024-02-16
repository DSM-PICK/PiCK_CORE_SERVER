package dsm.pick2024.domain.earlyreturn.service

import dsm.pick2024.domain.earlyreturn.domain.EarlyReturn
import dsm.pick2024.domain.earlyreturn.exception.EarlyReturnApplicationNotFoundException
import dsm.pick2024.domain.earlyreturn.port.`in`.StatusNOEarlyReturnUseCase
import dsm.pick2024.domain.earlyreturn.port.out.DeleteAllEarlyReturnListPort
import dsm.pick2024.domain.earlyreturn.port.out.FindEarlyReturnByIdPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class StatusNOEarlyReturnService(
    private val findEarlyReturnByIdPort: FindEarlyReturnByIdPort,
    private val deleteAllEarlyReturnPort: DeleteAllEarlyReturnListPort
) : StatusNOEarlyReturnUseCase {

    @Transactional
    override fun statusNOEarlyReturn(ids: List<UUID>) {
        val earlyReturnUpdate = mutableListOf<EarlyReturn>()

        for (earlyReturnId in ids) {
            val earlyReturn = findEarlyReturnByIdPort.findById(earlyReturnId)
                ?: throw EarlyReturnApplicationNotFoundException

            earlyReturnUpdate.add(earlyReturn)
        }
        deleteAllEarlyReturnPort.deleteAll(earlyReturnUpdate)
    }
}
