package dsm.pick2024.domain.user.facade

import dsm.pick2024.domain.user.domain.User
import dsm.pick2024.domain.user.exception.UserNotFoundException
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import dsm.pick2024.domain.user.port.out.QueryUserPort
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserFacade(
    private val queryUserPort: QueryUserPort
) : UserFacadeUseCase {

    override fun currentUser(): User {
        val accountId = SecurityContextHolder.getContext().authentication.name
        return getUserByAccountId(accountId)
    }

    override fun getUserByAccountId(accountId: String) =
        queryUserPort.findByAccountId(accountId) ?: throw UserNotFoundException

    override fun getUserByXquareId(userId: UUID) =
        queryUserPort.findByXquareId(userId) ?: throw UserNotFoundException
}
