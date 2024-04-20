package dsm.pick2024.domain.afterschool.persistence

import com.querydsl.jpa.impl.JPAQueryFactory
import dsm.pick2024.domain.afterschool.domain.AfterSchoolStudent
import dsm.pick2024.domain.afterschool.entity.QAfterSchoolStudentJpaEntity
import dsm.pick2024.domain.afterschool.mapper.AfterSchoolStudentMapper
import dsm.pick2024.domain.afterschool.persistence.repository.AfterSchoolStudentRepository
import dsm.pick2024.domain.afterschool.port.out.AfterSchoolStudentPortUser
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class AfterSchoolStudentPersistenceAdapter(
    private val afterSchoolStudentMapper: AfterSchoolStudentMapper,
    private val afterSchoolStudentRepository: AfterSchoolStudentRepository,
    private val jpaQueryFactory: JPAQueryFactory
) : AfterSchoolStudentPortUser {
    override fun saveAll(afterSchool: List<AfterSchoolStudent>) {
        val entities = afterSchool.map { afterSchoolStudentMapper.toEntity(it) }
        afterSchoolStudentRepository.saveAll(entities)
    }

    override fun findByUserId(id: UUID) =
        afterSchoolStudentRepository.findByUserId(id).let { afterSchoolStudentMapper.toDomain(it) }

    override fun deleteById(id: UUID) {
        afterSchoolStudentRepository.deleteByUserId(id)
    }

    override fun save(afterSchoolStudent: AfterSchoolStudent) {
        val entity = afterSchoolStudentMapper.toEntity(afterSchoolStudent)
        afterSchoolStudentRepository.save(entity)
    }

    override fun deleteAll() {
    }

    override fun findAll() =
        jpaQueryFactory
            .selectFrom(QAfterSchoolStudentJpaEntity.afterSchoolStudentJpaEntity)
            .orderBy(
                QAfterSchoolStudentJpaEntity.afterSchoolStudentJpaEntity.grade.asc(),
                QAfterSchoolStudentJpaEntity.afterSchoolStudentJpaEntity.classNum.asc(),
                QAfterSchoolStudentJpaEntity.afterSchoolStudentJpaEntity.num.asc()
            )
            .fetch()
            .map { afterSchoolStudentMapper.toDomain(it) }
}
