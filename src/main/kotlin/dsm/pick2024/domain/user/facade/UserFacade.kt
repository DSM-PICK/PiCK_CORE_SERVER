package dsm.pick2024.domain.user.facade

import dsm.pick2024.domain.user.domain.User
import dsm.pick2024.domain.user.exception.UserNotFoundException
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import dsm.pick2024.domain.user.port.out.FindByNamePort
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class UserFacade(
    private val findByNamePort: FindByNamePort
) : UserFacadeUseCase {

    override fun currentUser(): User {
        val name = SecurityContextHolder.getContext().authentication.name
        return getUserByName(name)
    }

    override fun getUserByName(name: String) =
        findByNamePort.findByName(name) ?: throw UserNotFoundException
}
