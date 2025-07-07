package dsm.pick2024.domain.user.service

import dsm.pick2024.domain.mail.port.`in`.VerifyMailUseCase
import dsm.pick2024.domain.user.port.`in`.PasswordResetUseCase
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import dsm.pick2024.domain.user.port.out.UpdateUserPort
import dsm.pick2024.domain.user.presentation.dto.request.PasswordResetRequest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class PasswordResetService(
    private val userFacadeUseCase: UserFacadeUseCase,
    private val verifyMailUseCase: VerifyMailUseCase,
    private val updateUserPort: UpdateUserPort,
    private val passwordEncoder: PasswordEncoder
) : PasswordResetUseCase {
    @Transactional
    override fun execute(request: PasswordResetRequest) {
        val user = userFacadeUseCase.currentUser()

        verifyMailUseCase.execute(request.code, user.accountId)

        updateUserPort.updateUserPassword(user.id!!, passwordEncoder.encode(request.password))
    }
}
