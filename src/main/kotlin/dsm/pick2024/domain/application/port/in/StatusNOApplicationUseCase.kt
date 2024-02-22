package dsm.pick2024.domain.application.port.`in`

import java.util.UUID

interface StatusNOApplicationUseCase {
    fun statusNOApplication(ids: List<UUID>)
}
