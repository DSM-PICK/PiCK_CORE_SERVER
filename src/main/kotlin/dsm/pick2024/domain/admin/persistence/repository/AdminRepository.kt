package dsm.pick2024.domain.admin.persistence.repository

import dsm.pick2024.domain.admin.entity.AdminJpaEntity
import org.springframework.data.repository.Repository
import java.util.UUID

interface AdminRepository : Repository<AdminJpaEntity, UUID> {
    fun findByName(name: String): AdminJpaEntity

    fun findByAdminId(adminId: String): AdminJpaEntity

    fun existsByAdminId(adminId: String): Boolean

    fun save(adminJpaEntity: AdminJpaEntity)
}
