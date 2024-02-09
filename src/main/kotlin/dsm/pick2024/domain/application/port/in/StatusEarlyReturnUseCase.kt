package dsm.pick2024.domain.application.port.`in`

import dsm.pick2024.domain.application.enums.Status
import java.util.UUID

interface StatusEarlyReturnUseCase {
    fun statusEarlyReturn(status: Status, earlyReturnIds: List<UUID>)
}
