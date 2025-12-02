package dsm.pick2024.domain.admin.service

import dsm.pick2024.domain.admin.port.`in`.AdminFinderUseCase
import dsm.pick2024.domain.admin.port.`in`.AdminLoginUseCase
import dsm.pick2024.domain.admin.port.out.AdminSavePort
import dsm.pick2024.domain.admin.presentation.dto.request.AdminLoginRequest
import dsm.pick2024.domain.user.exception.PasswordMissMatchException
import dsm.pick2024.global.security.jwt.JwtTokenProvider
import dsm.pick2024.global.security.jwt.dto.TokenResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Tag(name = "Admin API'")
@Service
class AdminLoginService(
    private val adminFinderUseCase: AdminFinderUseCase,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider,
    private val adminSavePort: AdminSavePort
) : AdminLoginUseCase {

    @Transactional
    override fun adminLogin(request: AdminLoginRequest): TokenResponse {
        val admin = adminFinderUseCase.findByAdminIdOrThrow(request.adminId)

        if (!passwordEncoder.matches(request.password, admin.password)) {
            throw PasswordMissMatchException
        }

        if (request.deviceToken != null && admin.deviceToken != "") {
            adminSavePort.save(admin.updateDeviceToken(request.deviceToken))
        }

        return jwtTokenProvider.generateToken(request.adminId, admin.role.name)
    }
}
