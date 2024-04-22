package dsm.pick2024.global.security.auth

import dsm.pick2024.domain.admin.port.`in`.AdminFacadeUseCase
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AdminDetailsService(
    private val adminFacadeUseCase: AdminFacadeUseCase
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val admin = adminFacadeUseCase.getAdminByAdminId(username)
        return AuthDetails(admin!!.adminId, admin.role)
    }
}
