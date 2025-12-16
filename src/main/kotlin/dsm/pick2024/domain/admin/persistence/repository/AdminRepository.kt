package dsm.pick2024.domain.admin.persistence.repository

import dsm.pick2024.domain.admin.entity.AdminJpaEntity
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface AdminRepository : CrudRepository<AdminJpaEntity, UUID> {
    fun existsByName(name: String): Boolean

    fun save(admin: AdminJpaEntity): AdminJpaEntity

    fun findByAdminId(adminId: String): AdminJpaEntity?

    fun existsByAdminId(adminId: String): Boolean

    fun findByGradeAndClassNum(
        grade: Int,
        classNum: Int
    ): AdminJpaEntity?

    fun findByName(name: String): AdminJpaEntity?

    fun existsByGradeAndClassNum(grade: Int, classNum: Int): Boolean
}
