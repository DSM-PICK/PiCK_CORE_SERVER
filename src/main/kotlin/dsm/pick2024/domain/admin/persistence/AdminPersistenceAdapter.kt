package dsm.pick2024.domain.admin.persistence

import com.querydsl.jpa.impl.JPAQueryFactory
import dsm.pick2024.domain.admin.domain.Admin
import dsm.pick2024.domain.admin.entity.QAdminJpaEntity
import dsm.pick2024.domain.admin.mapper.AdminMapper
import dsm.pick2024.domain.admin.persistence.repository.AdminRepository
import dsm.pick2024.domain.admin.port.out.AdminPort
import org.springframework.stereotype.Component
import java.util.UUID
import javax.transaction.Transactional

@Component
class AdminPersistenceAdapter(
    private val adminRepository: AdminRepository,
    private val adminMapper: AdminMapper,
    private val jpaQueryFactory: JPAQueryFactory
) : AdminPort {
    override fun existByName(name: String) = adminRepository.existsByName(name)

    override fun findByAdminId(adminId: String) =
        adminRepository.findByAdminId(adminId)?.let { adminMapper.toDomain(it) }

    override fun existsByAdminId(adminId: String) = adminRepository.existsByAdminId(adminId)

    override fun save(admin: Admin) {
        adminRepository.save(adminMapper.toEntity(admin))
    }

    override fun findByGradeAndClassNum(
        grade: Int,
        classNum: Int
    ): Admin? {
        return adminRepository.findByGradeAndClassNum(grade, classNum)?.let { adminMapper.toDomain(it) }
    }

    override fun findAll(): List<Admin> =
        jpaQueryFactory
            .selectFrom(QAdminJpaEntity.adminJpaEntity)
            .fetch()
            .map { adminMapper.toDomain(it) }

    override fun findByAdminName(name: String): Admin? =
        adminRepository.findByName(name)?.let { adminMapper.toDomain(it) }

    override fun isGradeAndClassRegistered(grade: Int, classNum: Int) =
        adminRepository.existsByGradeAndClassNum(grade, classNum)

    override fun deleteById(adminId: UUID) {
        admin
    }

    @Transactional
    override fun updateAdminPassword(adminId: UUID, password: String) {
        jpaQueryFactory
            .update(QAdminJpaEntity.adminJpaEntity)
            .set(QAdminJpaEntity.adminJpaEntity.password, password)
            .where(QAdminJpaEntity.adminJpaEntity.id.eq(adminId))
            .execute()
    }
}
