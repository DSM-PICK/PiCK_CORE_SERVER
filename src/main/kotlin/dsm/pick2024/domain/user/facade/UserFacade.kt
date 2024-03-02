package dsm.pick2024.domain.user.facade

import dsm.pick2024.domain.user.domain.User
import dsm.pick2024.domain.user.exception.UserNotFoundException
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import dsm.pick2024.domain.user.port.out.FindByAccountIdPort
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class UserFacade(
    private val findByAccountIdPort: FindByAccountIdPort
) : UserFacadeUseCase {

    override fun currentUser(): User {
        val accountId = SecurityContextHolder.getContext().authentication.name
        return getUserByAccountId(accountId)
    }

    override fun getUserByAccountId(accountId: String) =
        findByAccountIdPort.findByAccountId(accountId) ?: throw UserNotFoundException
}
