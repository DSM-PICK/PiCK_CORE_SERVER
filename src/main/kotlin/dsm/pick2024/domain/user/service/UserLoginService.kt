package dsm.pick2024.domain.user.service

import dsm.pick2024.domain.user.exception.PasswordMissMatchException
import dsm.pick2024.domain.user.port.`in`.UserFinderUseCase
import dsm.pick2024.domain.user.port.`in`.UserLoginUseCase
import dsm.pick2024.domain.user.port.out.UserSavePort
import dsm.pick2024.domain.user.presentation.dto.request.UserLoginRequest
import dsm.pick2024.global.security.jwt.JwtTokenProvider
import dsm.pick2024.global.security.jwt.dto.TokenResponse
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserLoginService(
    private val passwordEncoder: PasswordEncoder,
    private val userFinderUseCase: UserFinderUseCase,
    private val jwtTokenProvider: JwtTokenProvider,
    private val userSavePort: UserSavePort
) : UserLoginUseCase {

    @Transactional
    override fun execute(request: UserLoginRequest): TokenResponse {
        val user = userFinderUseCase.findByAccountIdOrThrow(request.accountId)

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw PasswordMissMatchException
        }

        userSavePort.save(user.updateDeviceToken(request.deviceToken))

        return jwtTokenProvider.generateToken(request.accountId, user.role.name)
    }
}
