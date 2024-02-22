package dsm.pick2024.domain.admin.facade

import dsm.pick2024.domain.admin.domain.Admin
import dsm.pick2024.domain.admin.exception.AdminNotFoundException
import dsm.pick2024.domain.admin.port.`in`.AdminFacadeUseCase
import dsm.pick2024.domain.admin.port.out.FindAdminByNamePort
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class AdminFacade(
    private val findByAdminIdPort: FindAdminByNamePort
) : AdminFacadeUseCase {

    override fun currentUser(): Admin {
        val adminId = SecurityContextHolder.getContext().authentication.name
        return getAdminByAdminId(adminId)
    }

    override fun getAdminByAdminId(adminId: String) =
        findByAdminIdPort.findByName(adminId) ?: throw AdminNotFoundException
}
