package dsm.pick2024.domain.user.service

import dsm.pick2024.domain.user.domain.User
import dsm.pick2024.domain.user.port.`in`.UserSignUpUseCase
import dsm.pick2024.domain.user.port.out.UserSavePort
import dsm.pick2024.domain.user.presentation.dto.request.UserSignUpRequest
import dsm.pick2024.global.security.jwt.JwtTokenProvider
import dsm.pick2024.global.security.jwt.dto.TokenResponse
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserSignUpService(
    private val savePort: UserSavePort,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider
) : UserSignUpUseCase {

    override fun execute(request: UserSignUpRequest): TokenResponse {
        val encodedPassword = passwordEncoder.encode(request.password)

        val user = User(
            accountId = request.accountId,
            password = encodedPassword,
            name = request.name,
            grade = request.grade,
            classNum = request.classNum,
            num = request.num,
            profile = null,
            role = request.role
        )

        val accountId = savePort.save(user).accountId

        return jwtTokenProvider.generateToken(accountId, request.role.name)
    }
}
