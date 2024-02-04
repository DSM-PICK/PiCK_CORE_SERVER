package dsm.pick2024.global.security.auth

import dsm.pick2024.domain.user.exception.UserNotFoundException
import dsm.pick2024.domain.user.port.out.FindByNamePort
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class AuthDetailsService(
    private val findByNamePort: FindByNamePort
) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        val user = findByNamePort.findByName(username!!) ?: throw UserNotFoundException

        return AuthDetails(user.name)
    }
}
