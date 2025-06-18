package dsm.pick2024.domain.application.port.`in`

import dsm.pick2024.domain.application.domain.Application
import java.util.*

interface ApplicationFinderUseCase {
    fun findByIdOrThrow(id: UUID): Application
    fun findByUserIdOrThrow(id: UUID): Application
}
