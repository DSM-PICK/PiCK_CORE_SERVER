package dsm.pick2024.domain.admin.service

import dsm.pick2024.domain.admin.facade.AdminFacade
import dsm.pick2024.domain.admin.port.out.DeleteAdminPort
import dsm.pick2024.domain.user.port.`in`.UserDeleteUseCase
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class AdminDeleteService(
    private val deleteAdminPort: DeleteAdminPort,
    private val adminFacade: AdminFacade
) : UserDeleteUseCase {

    @Transactional
    override fun delete() {
        val admin = adminFacade.currentAdmin()
        deleteAdminPort.deleteById(admin.id)
    }
}
