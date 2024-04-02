package dsm.pick2024.domain.application.persistence

import com.querydsl.jpa.impl.JPAQueryFactory
import dsm.pick2024.domain.application.domain.Application
import dsm.pick2024.domain.application.entity.QApplicationJapEntity
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.mapper.ApplicationMapper
import dsm.pick2024.domain.application.persistence.repository.ApplicationRepository
import dsm.pick2024.domain.application.port.out.ApplicationByStatusPort
import dsm.pick2024.domain.user.entity.QUserJpaEntity
import org.springframework.stereotype.Component
import java.util.*

@Component
class ApplicationPersistenceAdapterByStatus(
    private val applicationRepository: ApplicationRepository,
    private val applicationMapper: ApplicationMapper,
    private val jpaQueryFactory: JPAQueryFactory
) : ApplicationByStatusPort {
    override fun saveAll(application: List<Application>) {
        val entities = application.map { applicationMapper.toEntity(it) }
        applicationRepository.saveAll(entities)
    }

    override fun existsByUserId(userId: UUID) = applicationRepository.existsByUserId(userId)

    override fun existsOKByUserId(userId: UUID): Boolean {
        val application = QApplicationJapEntity.applicationJapEntity
        val user = QUserJpaEntity.userJpaEntity

        return jpaQueryFactory.selectFrom(application)
            .innerJoin(user).on(application.username.eq(user.name))
            .where(user.id.eq(userId), application.status.eq(Status.OK))
            .fetchFirst() != null
    }

    override fun findById(id: UUID) = applicationRepository.findById(id).let { applicationMapper.toDomain(it) }

    override fun deleteById(applicationId: UUID) {
        applicationRepository.deleteById(applicationId)
    }

    override fun deleteAll() {
        applicationRepository.deleteAll()
    }

    override fun findAll() = applicationRepository.findAll().map { applicationMapper.toDomain(it) }

    override fun findByUserId(userId: UUID) =
        applicationRepository.findByUserId(userId).let { applicationMapper.toDomain(it) }

    override fun save(application: Application) = applicationRepository.save(applicationMapper.toEntity(application))

    override fun findByFloor(floor: Int) =
        jpaQueryFactory
            .selectFrom(QApplicationJapEntity.applicationJapEntity)
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

    override fun findByGradeAndClassNum(
        grade: Int,
        classNum: Int
    ) = jpaQueryFactory
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

    override fun findOKApplication(id: UUID) =
        applicationRepository.findByUserIdAndStatus(id, Status.OK).let {
            applicationMapper.toDomain(it)
        }

    override fun findAllByStatus(status: Status) =
        jpaQueryFactory
            .selectFrom(QApplicationJapEntity.applicationJapEntity)
            .innerJoin(QUserJpaEntity.userJpaEntity)
            .on(QApplicationJapEntity.applicationJapEntity.username.eq(QUserJpaEntity.userJpaEntity.name))
            .where(
                QApplicationJapEntity.applicationJapEntity.status.eq(status)
            )
            .orderBy(
                QApplicationJapEntity.applicationJapEntity.grade.asc(),
                QApplicationJapEntity.applicationJapEntity.classNum.asc(),
                QApplicationJapEntity.applicationJapEntity.num.asc()
            )
            .fetch()
            .map { applicationMapper.toDomain(it) }
}
