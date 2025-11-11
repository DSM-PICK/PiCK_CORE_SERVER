package dsm.pick2024.domain.admin.facade

import dsm.pick2024.domain.admin.domain.Admin
import dsm.pick2024.domain.admin.port.`in`.AdminFacadeUseCase
import dsm.pick2024.domain.admin.port.`in`.AdminFinderUseCase
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class AdminFacade(
    private val adminFinderUseCase: AdminFinderUseCase
) : AdminFacadeUseCase {

    override fun currentAdmin(): Admin {
        val adminId = SecurityContextHolder.getContext().authentication.name
        return adminFinderUseCase.findByAdminIdOrThrow(adminId)
    }
}
