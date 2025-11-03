package dsm.pick2024.global.security.auth

import dsm.pick2024.domain.admin.port.`in`.AdminFinderUseCase
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AdminDetailsService(
    private val adminFinderUseCase: AdminFinderUseCase
) : UserDetailsService {
    override fun loadUserByUsername(adminId: String): UserDetails {
        val admin = adminFinderUseCase.findByAdminIdOrThrow(adminId)
        return AuthDetails(admin.adminId, admin.role)
    }
}
