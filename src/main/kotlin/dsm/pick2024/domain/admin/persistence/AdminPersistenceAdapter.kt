package dsm.pick2024.domain.admin.persistence

import dsm.pick2024.domain.admin.mapper.AdminMapper
import dsm.pick2024.domain.admin.persistence.repository.AdminRepository
import dsm.pick2024.domain.admin.port.out.AdminPort
import org.springframework.stereotype.Component

@Component
class AdminPersistenceAdapter(
    private val adminRepository: AdminRepository,
    private val adminMapper: AdminMapper
) : AdminPort {
    override fun findByName(name: String) =
        adminRepository.findByName(name).let { adminMapper.toDomain(it) }

    override fun findByAdminId(adminId: String) =
        adminRepository.findByAdminId(adminId).let { adminMapper.toDomain(it) }
}
