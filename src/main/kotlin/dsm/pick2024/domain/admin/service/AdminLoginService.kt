package dsm.pick2024.domain.admin.service

import dsm.pick2024.domain.admin.domain.Admin
import dsm.pick2024.domain.admin.exception.AdminNotFoundException
import dsm.pick2024.domain.admin.exception.NotAdminException
import dsm.pick2024.domain.admin.port.`in`.AdminLoginUseCase
import dsm.pick2024.domain.admin.port.out.ExistsByAdminIdPort
import dsm.pick2024.domain.admin.port.out.AdminSavePort
import dsm.pick2024.domain.admin.port.out.QueryAdminPort
import dsm.pick2024.domain.admin.presentation.dto.request.AdminLoginRequest
import dsm.pick2024.domain.user.entity.enums.Role
import dsm.pick2024.domain.user.exception.PasswordMissMatchException
import dsm.pick2024.domain.user.exception.UserNotFoundException
import dsm.pick2024.global.security.jwt.JwtTokenProvider
import dsm.pick2024.global.security.jwt.dto.TokenResponse
import dsm.pick2024.infrastructure.feign.client.XquareFeignClient
import dsm.pick2024.infrastructure.feign.client.dto.request.XquareRequest
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Tag(name = "Admin API'")
@Service
class AdminLoginService(
    private val queryAdminPort: QueryAdminPort,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider,
) : AdminLoginUseCase {

    @Transactional
    override fun adminLogin(adminLoginRequest: AdminLoginRequest): TokenResponse {
        val user = queryAdminPort.findByAdminId(adminLoginRequest.adminId) ?: throw AdminNotFoundException

        if (!passwordEncoder.matches(adminLoginRequest.password, user.password)) {
            throw PasswordMissMatchException
        }

        return jwtTokenProvider.generateToken(adminLoginRequest.adminId, user.role.name)
    }
}
