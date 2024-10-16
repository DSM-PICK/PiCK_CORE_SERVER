package dsm.pick2024.global.security.auth

import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class AuthDetailsService(
    private val userFacadeUseCase: UserFacadeUseCase
) : UserDetailsService {
    override fun loadUserByUsername(accountId: String): UserDetails {
        val user = userFacadeUseCase.getUserByAccountId(accountId)
        return AuthDetails(user.accountId, user.role)
    }
}
