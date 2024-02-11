package dsm.pick2024.domain.application.persistence

import com.querydsl.jpa.impl.JPAQueryFactory
import dsm.pick2024.domain.application.domain.Application
import dsm.pick2024.domain.application.entity.QApplicationJapEntity
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.mapper.ApplicationMapper
import dsm.pick2024.domain.application.persistence.repository.ApplicationRepository
import dsm.pick2024.domain.application.port.out.ApplicationPort
import dsm.pick2024.domain.user.entity.QUserJpaEntity
import org.springframework.stereotype.Component
import java.util.*

@Component
class ApplicationPersistenceAdapter(
    private val applicationRepository: ApplicationRepository,
    private val applicationMapper: ApplicationMapper,
    private val jpaQueryFactory: JPAQueryFactory
) : ApplicationPort {
    override fun saveAll(application: List<Application>) {
        val entities = application.map { applicationMapper.toEntity(it) }
        applicationRepository.saveAll(entities)
    }

    override fun existsByUsername(username: String) = applicationRepository.existsByUsername(username)

    override fun findById(id: UUID) =
        applicationRepository.findById(id).let { applicationMapper.toDomain(it) }

    override fun deleteById(applicationId: UUID) {
        applicationRepository.deleteById(applicationId)
    }

    override fun deleteAll(application: List<Application>) {
        val entities: List<Application> = application
        applicationRepository.deleteAll(entities)
    }

    override fun save(application: Application) =
        applicationRepository.save(applicationMapper.toEntity(application))

    override fun findByFloor(floor: Int) =
        jpaQueryFactory
            .select(QApplicationJapEntity.applicationJapEntity)
            .innerJoin(QUserJpaEntity.userJpaEntity)
            .on(QApplicationJapEntity.applicationJapEntity.username.eq(QUserJpaEntity.userJpaEntity.name))
            .where(
                QUserJpaEntity.userJpaEntity.grade.eq(
                    when (floor) {
                        4 -> 1
                        3 -> 2
                        2 -> 3
                        else -> throw IllegalArgumentException("Invalid floor number")
                    }
                ),
                QApplicationJapEntity.applicationJapEntity.status.eq(Status.QUIET)
            )
            .fetch()
            .map { applicationMapper.toDomain(it) }

    override fun findByGradeAndClassNum(grade: Int, classNum: Int) =
        jpaQueryFactory
            .selectFrom(QApplicationJapEntity.applicationJapEntity)
            .innerJoin(QUserJpaEntity.userJpaEntity)
            .on(QApplicationJapEntity.applicationJapEntity.username.eq(QUserJpaEntity.userJpaEntity.name))
            .where(
                QUserJpaEntity.userJpaEntity.grade.eq(grade),
                QUserJpaEntity.userJpaEntity.classNum.eq(classNum),
                QApplicationJapEntity.applicationJapEntity.status.eq(Status.QUIET)
            )
            .fetch()
            .map { applicationMapper.toDomain(it) }
}
