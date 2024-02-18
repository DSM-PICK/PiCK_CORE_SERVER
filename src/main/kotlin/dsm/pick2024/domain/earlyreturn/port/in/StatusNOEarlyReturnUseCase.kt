package dsm.pick2024.domain.earlyreturn.port.`in`

import java.util.UUID

interface StatusNOEarlyReturnUseCase {
    fun statusNOEarlyReturn(ids: List<UUID>)
}
