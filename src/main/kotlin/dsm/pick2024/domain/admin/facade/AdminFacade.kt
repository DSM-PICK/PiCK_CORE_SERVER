package dsm.pick2024.domain.admin.facade

import dsm.pick2024.domain.admin.domain.Admin
import dsm.pick2024.domain.admin.port.`in`.AdminFacadeUseCase
import dsm.pick2024.domain.admin.port.out.QueryAdminPort
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class AdminFacade(
    private val queryAdminPort: QueryAdminPort
) : AdminFacadeUseCase {

    override fun currentAdmin(): Admin {
        val name = SecurityContextHolder.getContext().authentication.name
        return getAdminByAdminId(name)
    }
}
