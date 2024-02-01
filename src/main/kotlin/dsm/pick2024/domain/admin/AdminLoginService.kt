package dsm.pick2024.domain.admin

import dsm.pick2024.domain.admin.exception.AdminNotFoundException
import dsm.pick2024.domain.admin.port.`in`.AdminLoginUseCase
import dsm.pick2024.domain.admin.port.out.FindByAdminIdPort
import dsm.pick2024.domain.admin.presentation.dto.request.AdminLoginRequest
import dsm.pick2024.domain.user.entity.enums.Role
import dsm.pick2024.domain.user.exception.PasswordMissMatchException
import dsm.pick2024.global.security.jwt.JwtTokenProvider
import dsm.pick2024.global.security.jwt.dto.TokenResponse
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminLoginService(
    private val findByAdminIdPort: FindByAdminIdPort,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider
) : AdminLoginUseCase {
    @Transactional(readOnly = true)
    override fun adminLogin(adminLoginRequest: AdminLoginRequest): TokenResponse {
        val admin = findByAdminIdPort.findByAdminId(adminLoginRequest.adminId) ?: throw AdminNotFoundException
        if (passwordEncoder.matches(adminLoginRequest.password, admin.password)) {
            throw PasswordMissMatchException
        }
        val token = jwtTokenProvider.getToken(admin.name, Role.ADMIN.toString())
        return token
    }
}
