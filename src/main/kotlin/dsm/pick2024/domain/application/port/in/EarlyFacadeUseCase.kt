package dsm.pick2024.domain.application.port.`in`

import dsm.pick2024.domain.application.domain.EarlyReturn

interface EarlyFacadeUseCase {
    fun getUsername(username: String): EarlyReturn
}
