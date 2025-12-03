package dsm.pick2024.domain.admin.service

import dsm.pick2024.domain.admin.port.`in`.AdminFinderUseCase
import dsm.pick2024.domain.admin.port.`in`.AdminLoginUseCase
import dsm.pick2024.domain.admin.presentation.dto.request.AdminLoginRequest
import dsm.pick2024.domain.devicetoken.domain.AdminDeviceToken
import dsm.pick2024.domain.devicetoken.port.out.AdminDeviceTokenPort
import dsm.pick2024.domain.user.exception.PasswordMissMatchException
import dsm.pick2024.global.security.jwt.JwtTokenProvider
import dsm.pick2024.global.security.jwt.dto.TokenResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Tag(name = "Admin API'")
@Service
class AdminLoginService(
    private val adminFinderUseCase: AdminFinderUseCase,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider,
    private val adminDeviceTokenPort: AdminDeviceTokenPort
) : AdminLoginUseCase {

    @Transactional
    override fun adminLogin(adminLoginRequest: AdminLoginRequest): TokenResponse {
        val admin = adminFinderUseCase.findByAdminIdOrThrow(adminLoginRequest.adminId)

        if (!passwordEncoder.matches(adminLoginRequest.password, admin.password)) {
            throw PasswordMissMatchException
        }

        adminLoginRequest.deviceToken?.let { token ->
            val existingTokens = adminDeviceTokenPort.findAllByAdminId(admin.id)
            if (existingTokens.none { it.deviceToken == token }) {
                val os = adminLoginRequest.os
                val adminDeviceToken = AdminDeviceToken(
                    id = UUID.randomUUID(),
                    adminId = admin.id,
                    deviceToken = token,
                    os = os
                )
                adminDeviceTokenPort.save(adminDeviceToken)
            }
        }

        return jwtTokenProvider.generateToken(adminLoginRequest.adminId, admin.role.name)
    }
}
