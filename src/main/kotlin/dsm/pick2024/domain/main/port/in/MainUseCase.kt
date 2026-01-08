package dsm.pick2024.domain.main.port.`in`

import java.util.UUID

interface MainUseCase {
    fun sendEvent(userId: UUID): Any?
}
