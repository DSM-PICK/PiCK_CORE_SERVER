package dsm.pick2024.domain.afterschool.persistence.repository

import dsm.pick2024.domain.afterschool.entity.AfterSchoolStudentJpaEntity
import org.springframework.data.repository.Repository
import java.util.UUID

interface AfterSchoolStudentRepository : Repository<AfterSchoolStudentJpaEntity, UUID> {
    fun findByUser_Id(id: UUID): AfterSchoolStudentJpaEntity

    fun findById(id: UUID): AfterSchoolStudentJpaEntity

    fun saveAll(entity: Iterable<AfterSchoolStudentJpaEntity>)

    fun deleteById(id: UUID)

    fun save(entity: AfterSchoolStudentJpaEntity)

    fun deleteAll()
}
