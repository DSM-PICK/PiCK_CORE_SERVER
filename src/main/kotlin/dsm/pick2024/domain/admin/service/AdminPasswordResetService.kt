package dsm.pick2024.domain.admin.service

import dsm.pick2024.domain.admin.port.`in`.AdminFacadeUseCase
import dsm.pick2024.domain.admin.port.`in`.AdminPasswordResetUseCase
import dsm.pick2024.domain.admin.port.out.UpdateAdminPort
import dsm.pick2024.domain.admin.presentation.dto.request.AdminPasswordResetRequest
import dsm.pick2024.domain.mail.port.`in`.VerifyMailUseCase
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class AdminPasswordResetService(
    private val adminFacadeUseCase: AdminFacadeUseCase,
    private val verifyMailUseCase: VerifyMailUseCase,
    private val updateAdminPort: UpdateAdminPort,
    private val passwordEncoder: PasswordEncoder
) : AdminPasswordResetUseCase {

    @Transactional
    override fun execute(request: AdminPasswordResetRequest) {
        val admin = adminFacadeUseCase.getAdminByAdminId(request.adminId)

        verifyMailUseCase.verifyAndConsume(request.code, admin.adminId)

        updateAdminPort.updateAdminPassword(admin.id, passwordEncoder.encode(request.password))
    }
}
