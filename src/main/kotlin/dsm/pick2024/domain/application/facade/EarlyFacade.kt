package dsm.pick2024.domain.application.facade

import dsm.pick2024.domain.application.exception.EarlyReturnApplicationNotFoundException
import dsm.pick2024.domain.application.port.`in`.EarlyFacadeUseCase
import dsm.pick2024.domain.application.port.out.FindEarlyReturnByUsernamePort
import org.springframework.stereotype.Component

@Component
class EarlyFacade(
    private val findEarlyReturnByUsernamePort: FindEarlyReturnByUsernamePort
): EarlyFacadeUseCase {

    override fun getUsername(username: String) =
        findEarlyReturnByUsernamePort.findByUsername(username) ?: throw EarlyReturnApplicationNotFoundException

}
