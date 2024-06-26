package dsm.pick2024.domain.admin.persistence.repository

import dsm.pick2024.domain.admin.entity.AdminJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface AdminRepository : JpaRepository<AdminJpaEntity, UUID> {
    fun existsByName(name: String): Boolean

    fun findByAdminId(adminId: String): AdminJpaEntity

    fun existsByAdminId(adminId: String): Boolean

    fun findByGradeAndClassNum(
        grade: Int,
        classNum: Int
    ): AdminJpaEntity
}
