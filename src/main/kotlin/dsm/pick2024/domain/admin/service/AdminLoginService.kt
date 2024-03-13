package dsm.pick2024.domain.admin.service

import dsm.pick2024.domain.admin.domain.Admin
import dsm.pick2024.domain.admin.exception.AdminNotFoundException
import dsm.pick2024.domain.admin.port.`in`.AdminLoginUseCase
import dsm.pick2024.domain.admin.port.out.ExistsByAdminIdPort
import dsm.pick2024.domain.admin.port.out.FindByAdminIdPort
import dsm.pick2024.domain.admin.port.out.SavePort
import dsm.pick2024.domain.admin.presentation.dto.request.AdminLoginRequest
import dsm.pick2024.domain.user.entity.enums.Role
import dsm.pick2024.domain.user.exception.PasswordMissMatchException
import dsm.pick2024.global.security.jwt.JwtTokenProvider
import dsm.pick2024.global.security.jwt.dto.TokenResponse
import dsm.pick2024.infrastructure.feign.client.XquareFeignClient
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminLoginService(
    private val findByAdminIdPort: FindByAdminIdPort,
    private val passwordEncoder: PasswordEncoder,
    private val existsByAdminIdPort: ExistsByAdminIdPort,
    private val xquareFeignClient: XquareFeignClient,
    private val jwtTokenProvider: JwtTokenProvider,
    private val savePort: SavePort
) : AdminLoginUseCase {
    @Transactional(readOnly = true)
    override fun adminLogin(adminLoginRequest: AdminLoginRequest): TokenResponse {
        if (!existsByAdminIdPort.existsByAdminId(adminLoginRequest.adminId)) {
            val xquareUser = xquareFeignClient.xquareUser(adminLoginRequest.adminId, adminLoginRequest.password)
            savePort.save(
                Admin(
                    adminId = xquareUser.accountId,
                    password = passwordEncoder.encode(xquareUser.password),
                    name = xquareUser.name,
                    role = Role.SCH
                )
            )
            jwtTokenProvider.generateToken(xquareUser.name, Role.SCH.toString())
        }

        val admin = findByAdminIdPort.findByAdminId(adminLoginRequest.adminId) ?: throw AdminNotFoundException
        if ((adminLoginRequest.password != admin.password)) {
            throw PasswordMissMatchException
        }
        val token = jwtTokenProvider.generateToken(admin.adminId, Role.SCH.toString())
        return token
    }
}
