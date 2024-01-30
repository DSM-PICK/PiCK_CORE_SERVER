package dsm.pick2024.domain.admin.persistence

import dsm.pick2024.domain.admin.domain.Admin
import dsm.pick2024.domain.admin.mapper.AdminMapper
import dsm.pick2024.domain.admin.persistence.repository.AdminRepository
import dsm.pick2024.domain.admin.port.out.AdminPort
import org.springframework.stereotype.Component

@Component
class AdminPersistenceAdapter(
    private val adminRepository: AdminRepository,
    private val adminMapper: AdminMapper
) : AdminPort {
    override fun findByAdminId(adminId: String): Admin {
        return adminRepository.findByAdminId(adminId).let(adminMapper::toDomain)
    }
}
