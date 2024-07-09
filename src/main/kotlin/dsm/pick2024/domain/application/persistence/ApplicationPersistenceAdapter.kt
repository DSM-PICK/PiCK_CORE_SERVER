package dsm.pick2024.domain.application.persistence

import com.querydsl.jpa.impl.JPAQueryFactory
import dsm.pick2024.domain.application.domain.Application
import dsm.pick2024.domain.application.entity.QApplicationJapEntity
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.mapper.ApplicationMapper
import dsm.pick2024.domain.application.persistence.repository.ApplicationRepository
import dsm.pick2024.domain.application.port.out.ApplicationPort
import dsm.pick2024.domain.classroom.exception.FloorNotFoundException
import dsm.pick2024.domain.attendance.port.out.QueryAttendancePort
import dsm.pick2024.domain.user.entity.QUserJpaEntity
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ApplicationPersistenceAdapter(
    private val applicationRepository: ApplicationRepository,
    private val applicationMapper: ApplicationMapper,
    private val jpaQueryFactory: JPAQueryFactory,
    private val attendancePort: QueryAttendancePort
) : ApplicationPort {
    override fun saveAll(application: List<Application>) {
        val entities = application.map { applicationMapper.toEntity(it) }
        applicationRepository.saveAll(entities)
    }

    override fun existsByUserId(userId: UUID) = applicationRepository.existsByUserId(userId)

    override fun existsOKByUserId(userId: UUID): Boolean {
        val application = QApplicationJapEntity.applicationJapEntity
        val user = QUserJpaEntity.userJpaEntity

        return jpaQueryFactory.selectFrom(application)
            .innerJoin(user).on(application.userName.eq(user.name))
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

    override fun findAll() =
        applicationRepository.findAll().map { applicationMapper.toDomain(it) }

    override fun findByUserId(userId: UUID) =
        applicationRepository.findByUserId(userId).let { applicationMapper.toDomain(it) }

    override fun findOKApplication(id: UUID) =
        applicationRepository.findByUserIdAndStatus(id, Status.OK).let {
            applicationMapper.toDomain(it)
        }

    override fun save(application: Application) = applicationRepository.save(applicationMapper.toEntity(application))

    override fun findByFloor(floor: Int) =
        jpaQueryFactory
            .selectFrom(QApplicationJapEntity.applicationJapEntity)
            .innerJoin(QUserJpaEntity.userJpaEntity)
            .on(
                QApplicationJapEntity.applicationJapEntity.grade.eq(QUserJpaEntity.userJpaEntity.grade)
                    .and(QApplicationJapEntity.applicationJapEntity.classNum.eq(QUserJpaEntity.userJpaEntity.classNum))
                    .and(QApplicationJapEntity.applicationJapEntity.num.eq(QUserJpaEntity.userJpaEntity.num))
            ).where(
                QUserJpaEntity.userJpaEntity.grade.eq(
                    when (floor) {
                        4 -> 1
                        3 -> 2
                        2 -> 3
                        else -> throw FloorNotFoundException
                    }
                )
            )
            .fetch()
            .map { applicationMapper.toDomain(it) }

    override fun queryApplicationWithAttendance(floor: Int): List<Application> {
        val attendances = attendancePort.findByFloor(floor)
        val userIds = attendances?.map { it.userId }

        val applications = jpaQueryFactory
            .selectFrom(QApplicationJapEntity.applicationJapEntity)
            .innerJoin(QUserJpaEntity.userJpaEntity)
            .on(
                QApplicationJapEntity.applicationJapEntity.grade.eq(QUserJpaEntity.userJpaEntity.grade)
                    .and(QApplicationJapEntity.applicationJapEntity.classNum.eq(QUserJpaEntity.userJpaEntity.classNum))
                    .and(QApplicationJapEntity.applicationJapEntity.num.eq(QUserJpaEntity.userJpaEntity.num))
            ).where(
                QUserJpaEntity.userJpaEntity.id.`in`(userIds)
            )
            .fetch()

        return applications.map { applicationMapper.toDomain(it) }
    }

    override fun findByGradeAndClassNum(
        grade: Int,
        classNum: Int
    ) = jpaQueryFactory
        .selectFrom(QApplicationJapEntity.applicationJapEntity)
        .innerJoin(QUserJpaEntity.userJpaEntity)
        .on(
            QApplicationJapEntity.applicationJapEntity.grade.eq(QUserJpaEntity.userJpaEntity.grade)
                .and(QApplicationJapEntity.applicationJapEntity.classNum.eq(QUserJpaEntity.userJpaEntity.classNum))
                .and(QApplicationJapEntity.applicationJapEntity.num.eq(QUserJpaEntity.userJpaEntity.num))
        )
        .where(
            QUserJpaEntity.userJpaEntity.grade.eq(grade),
            QUserJpaEntity.userJpaEntity.classNum.eq(classNum),
            QApplicationJapEntity.applicationJapEntity.status.eq(Status.QUIET)
        )
        .fetch()
        .map { applicationMapper.toDomain(it) }

    override fun findAllByStatus(status: Status) =
        jpaQueryFactory
            .selectFrom(QApplicationJapEntity.applicationJapEntity)
            .innerJoin(QUserJpaEntity.userJpaEntity)
            .on(
                QApplicationJapEntity.applicationJapEntity.grade.eq(QUserJpaEntity.userJpaEntity.grade)
                    .and(QApplicationJapEntity.applicationJapEntity.classNum.eq(QUserJpaEntity.userJpaEntity.classNum))
                    .and(QApplicationJapEntity.applicationJapEntity.num.eq(QUserJpaEntity.userJpaEntity.num))
            )
            .where(
                QApplicationJapEntity.applicationJapEntity.status.eq(status)
            )
            .fetch()
            .map { applicationMapper.toDomain(it) }
}
