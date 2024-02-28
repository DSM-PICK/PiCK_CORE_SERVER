package dsm.pick2024.domain.user.service

import dsm.pick2024.domain.user.entity.enums.Role
import dsm.pick2024.domain.user.exception.PasswordMissMatchException
import dsm.pick2024.domain.user.exception.UserNotFoundException
import dsm.pick2024.domain.user.port.`in`.LoginUseCase
import dsm.pick2024.domain.user.port.out.FindByAccountIdPort
import dsm.pick2024.domain.user.presentation.dto.request.UserLoginRequest
import dsm.pick2024.global.security.jwt.JwtTokenProvider
import dsm.pick2024.global.security.jwt.dto.TokenResponse
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserLoginService(
    private val passwordEncoder: PasswordEncoder,
    private val findByAccountIdPort: FindByAccountIdPort,
    private val jwtTokenProvider: JwtTokenProvider
) : LoginUseCase {

    @Transactional
    override fun login(userLoginRequest: UserLoginRequest): TokenResponse {
        val user = findByAccountIdPort.findByAccountId(userLoginRequest.accountId) ?: throw UserNotFoundException
        if ((userLoginRequest.password != user.password)) {
            throw PasswordMissMatchException
        }
        val token = jwtTokenProvider.generateToken(user.name, Role.STU.toString())
        return token
    }
}
