package dsm.pick2024.domain.afterschool.persistence

import com.querydsl.jpa.impl.JPAQueryFactory
import dsm.pick2024.domain.afterschool.domain.AfterSchoolStudent
import dsm.pick2024.domain.afterschool.entity.QAfterSchoolStudentJpaEntity
import dsm.pick2024.domain.afterschool.mapper.AfterSchoolStudentMapper
import dsm.pick2024.domain.afterschool.persistence.repository.AfterSchoolStudentRepository
import dsm.pick2024.domain.afterschool.port.out.AfterSchoolStudentPort
import dsm.pick2024.domain.user.exception.UserNotFoundException
import dsm.pick2024.domain.user.persistence.repository.UserRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class AfterSchoolStudentPersistenceAdapterStudent(
    private val afterSchoolStudentMapper: AfterSchoolStudentMapper,
    private val afterSchoolStudentRepository: AfterSchoolStudentRepository,
    private val jpaQueryFactory: JPAQueryFactory,
    private val userRepository: UserRepository
) : AfterSchoolStudentPort {
    override fun saveAll(afterSchool: List<AfterSchoolStudent>) {
        val entities = afterSchool.map {
            val user = userRepository.findById(it.userId) ?: throw UserNotFoundException
            afterSchoolStudentMapper.toEntity(it, user)
        }
        afterSchoolStudentRepository.saveAll(entities)
    }

    override fun findByUserId(id: UUID) =
        afterSchoolStudentRepository.findByUser_Id(id).let { afterSchoolStudentMapper.toDomain(it) }

    override fun findById(id: UUID) =
        afterSchoolStudentRepository.findById(id).let { afterSchoolStudentMapper.toDomain(it) }

    override fun deleteById(id: UUID) {
        afterSchoolStudentRepository.deleteById(id)
    }

    override fun save(afterSchoolStudent: AfterSchoolStudent) {
        val user = userRepository.findById(afterSchoolStudent.userId) ?: throw UserNotFoundException
        val entity = afterSchoolStudentMapper.toEntity(afterSchoolStudent, user)
        afterSchoolStudentRepository.save(entity)
    }

    override fun deleteAll() {
    }

    override fun findAll() =
        jpaQueryFactory
            .selectFrom(QAfterSchoolStudentJpaEntity.afterSchoolStudentJpaEntity)
            .fetch()
            .map { afterSchoolStudentMapper.toDomain(it) }
}
