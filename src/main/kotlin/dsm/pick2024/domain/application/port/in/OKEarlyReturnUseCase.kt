package dsm.pick2024.domain.application.port.`in`

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.service.OKEarlyReturnService

interface OKEarlyReturnUseCase {
    fun okEarlyReturn(status: Status, name: String)
}
