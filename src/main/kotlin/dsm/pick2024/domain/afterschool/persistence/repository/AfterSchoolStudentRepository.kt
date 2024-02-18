package dsm.pick2024.domain.afterschool.persistence.repository

import dsm.pick2024.domain.afterschool.entity.AfterSchoolStudentJpaEntity
import org.springframework.data.repository.Repository
import java.util.UUID

interface AfterSchoolStudentRepository : Repository<AfterSchoolStudentJpaEntity, UUID> {
    fun findById(id: UUID): AfterSchoolStudentJpaEntity

    fun saveAll(entity: Iterable<AfterSchoolStudentJpaEntity>)

    fun findAll(): List<AfterSchoolStudentJpaEntity>

    fun deleteById(id: UUID)
}
