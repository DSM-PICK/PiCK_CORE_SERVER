package dsm.pick2024.domain.afterschool.persistence

import dsm.pick2024.domain.afterschool.domain.AfterSchoolStudent
import dsm.pick2024.domain.afterschool.entity.AfterSchoolStudentJpaEntity
import dsm.pick2024.domain.afterschool.mapper.AfterSchoolStudentMapper
import dsm.pick2024.domain.afterschool.persistence.repository.AfterSchoolStudentRepository
import dsm.pick2024.domain.afterschool.port.out.AfterSchoolStudentPortUser
import org.springframework.stereotype.Component
import java.util.*

@Component
class AfterSchoolStudentPersistenceAdapterUser(
    private val afterSchoolStudentMapper: AfterSchoolStudentMapper,
    private val afterSchoolStudentRepository: AfterSchoolStudentRepository
) : AfterSchoolStudentPortUser {
    override fun saveAll(afterSchool: MutableList<AfterSchoolStudentJpaEntity>) {
        afterSchoolStudentRepository.saveAll(afterSchool)
    }

    override fun findByUserId(id: UUID) =
        afterSchoolStudentRepository.findByUserId(id).let { afterSchoolStudentMapper.toDomain(it) }

    override fun deleteById(id: UUID) {
        afterSchoolStudentRepository.deleteById(id)
    }

    override fun findByAll() = afterSchoolStudentRepository.findAll().map { afterSchoolStudentMapper.toDomain(it) }

    override fun save(entity: AfterSchoolStudent) {
        afterSchoolStudentRepository.save(afterSchoolStudentMapper.toEntity(entity))
    }
}
