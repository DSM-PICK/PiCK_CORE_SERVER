package dsm.pick2024.domain.user.service

import dsm.pick2024.domain.user.domain.User
import dsm.pick2024.domain.user.port.`in`.CreateUserUseCase
import dsm.pick2024.domain.user.port.out.UserSavePort
import dsm.pick2024.domain.user.presentation.dto.request.UserSignUpRequest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserSignUpService (
    private val savePort: UserSavePort,
    private val passwordEncoder: PasswordEncoder
) : CreateUserUseCase{

    override fun createUser(request: UserSignUpRequest): UUID {
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

        return savePort.save(user).id!!
    }
}
