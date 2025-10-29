package dsm.pick2024.domain.application.persistence

import com.querydsl.jpa.impl.JPAQueryFactory
import dsm.pick2024.domain.application.domain.Application
import dsm.pick2024.domain.application.entity.QApplicationJpaEntity
import dsm.pick2024.domain.application.enums.ApplicationKind
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.mapper.ApplicationMapper
import dsm.pick2024.domain.application.persistence.repository.ApplicationRepository
import dsm.pick2024.domain.application.port.out.ApplicationPort
import dsm.pick2024.domain.classroom.exception.FloorNotFoundException
import dsm.pick2024.domain.attendance.port.out.QueryAttendancePort
import dsm.pick2024.domain.user.entity.QUserJpaEntity
import dsm.pick2024.domain.user.exception.UserNotFoundException
import dsm.pick2024.domain.user.persistence.repository.UserRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ApplicationPersistenceAdapter(
    private val applicationRepository: ApplicationRepository,
    private val applicationMapper: ApplicationMapper,
    private val jpaQueryFactory: JPAQueryFactory,
    private val attendancePort: QueryAttendancePort,
    private val userRepository: UserRepository
) : ApplicationPort {
    override fun saveAll(application: List<Application>) {
        val entities = application.map {
            val user = userRepository.findById(it.userId) ?: throw UserNotFoundException
            applicationMapper.toEntity(it, user)
        }
        applicationRepository.saveAll(entities)
    }

    override fun existByUserId(userId: UUID) = applicationRepository.existsByUser_Id(userId)

    override fun existsByUserIdAndApplicationKind(userId: UUID, applicationKind: ApplicationKind) =
        applicationRepository.existsByUser_IdAndApplicationKind(userId, applicationKind)

    override fun existsByStatusAndUserIdAndApplicationKind(
        status: Status,
        userId: UUID,
        applicationKind: ApplicationKind
    ) =
        applicationRepository.existsByStatusAndUser_IdAndApplicationKind(status, userId, applicationKind)

    override fun findById(id: UUID): Application? {
        return applicationRepository.findById(id)?.let { applicationMapper.toDomain(it) }
    }

    override fun deleteByIdAndApplicationKind(applicationId: UUID, applicationKind: ApplicationKind) {
        applicationRepository.deleteByIdAndApplicationKind(applicationId, applicationKind)
    }

    override fun deleteAll() =
        applicationRepository.deleteAll()

    override fun deleteAllByApplicationKind(applicationKind: ApplicationKind) {
        applicationRepository.deleteAllByApplicationKind(applicationKind)
    }

    override fun findByUserId(userId: UUID) =

        applicationRepository.findByUser_Id(userId)?.let { applicationMapper.toDomain(it) }

    override fun findAllByApplicationKind(applicationKind: ApplicationKind) =
        applicationRepository.findAllByApplicationKind(applicationKind).map { applicationMapper.toDomain(it) }

    override fun findByUserIdAndStatusAndApplicationKind(
        status: Status,
        id: UUID,
        applicationKind: ApplicationKind
    ): Application? {
        return applicationRepository.findByUser_IdAndStatusAndApplicationKind(
            id,
            status,
            applicationKind
        )?.let { applicationMapper.toDomain(it) }
    }

    override fun save(application: Application) {
        val user = userRepository.findById(application.userId) ?: throw UserNotFoundException
        applicationRepository.save(applicationMapper.toEntity(application, user))
    }

    override fun findByFloorAndApplicationKind(floor: Int, applicationKind: ApplicationKind) =
        jpaQueryFactory
            .selectFrom(QApplicationJpaEntity.applicationJpaEntity)
            .innerJoin(QUserJpaEntity.userJpaEntity)
            .on(
                QApplicationJpaEntity.applicationJpaEntity.grade.eq(QUserJpaEntity.userJpaEntity.grade)
                    .and(QApplicationJpaEntity.applicationJpaEntity.classNum.eq(QUserJpaEntity.userJpaEntity.classNum))
                    .and(QApplicationJpaEntity.applicationJpaEntity.num.eq(QUserJpaEntity.userJpaEntity.num))
            ).where(
                QUserJpaEntity.userJpaEntity.grade.eq(
                    when (floor) {
                        4 -> 1
                        3 -> 2
                        2 -> 3
                        else -> throw FloorNotFoundException
                    }
                ),
                QApplicationJpaEntity.applicationJpaEntity.applicationKind.eq(applicationKind)
            )
            .fetch()
            .map { applicationMapper.toDomain(it) }

    override fun queryApplicationWithAttendance(floor: Int): List<Application> {
        val attendances = attendancePort.findByFloor(floor)
        val userIds = attendances?.map { it.userId }

        val applications = jpaQueryFactory
            .selectFrom(QApplicationJpaEntity.applicationJpaEntity)
            .innerJoin(QUserJpaEntity.userJpaEntity)
            .on(
                QApplicationJpaEntity.applicationJpaEntity.grade.eq(QUserJpaEntity.userJpaEntity.grade)
                    .and(QApplicationJpaEntity.applicationJpaEntity.classNum.eq(QUserJpaEntity.userJpaEntity.classNum))
                    .and(QApplicationJpaEntity.applicationJpaEntity.num.eq(QUserJpaEntity.userJpaEntity.num))
            ).where(
                QUserJpaEntity.userJpaEntity.id.`in`(userIds)
            )
            .fetch()

        return applications.map { applicationMapper.toDomain(it) }
    }

    override fun findByGradeAndClassNumAndApplicationKind(
        grade: Int,
        classNum: Int,
        applicationKind: ApplicationKind
    ) = jpaQueryFactory
        .selectFrom(QApplicationJpaEntity.applicationJpaEntity)
        .innerJoin(QUserJpaEntity.userJpaEntity)
        .on(
            QApplicationJpaEntity.applicationJpaEntity.grade.eq(QUserJpaEntity.userJpaEntity.grade)
                .and(QApplicationJpaEntity.applicationJpaEntity.classNum.eq(QUserJpaEntity.userJpaEntity.classNum))
                .and(QApplicationJpaEntity.applicationJpaEntity.num.eq(QUserJpaEntity.userJpaEntity.num))
        )
        .where(
            QUserJpaEntity.userJpaEntity.grade.eq(grade),
            QUserJpaEntity.userJpaEntity.classNum.eq(classNum),
            QApplicationJpaEntity.applicationJpaEntity.status.eq(Status.QUIET),
            QApplicationJpaEntity.applicationJpaEntity.applicationKind.eq(applicationKind)
        )
        .fetch()
        .map { applicationMapper.toDomain(it) }

    override fun findAllByStatusAndApplicationKind(status: Status, applicationKind: ApplicationKind) =
        jpaQueryFactory
            .selectFrom(QApplicationJpaEntity.applicationJpaEntity)
            .innerJoin(QUserJpaEntity.userJpaEntity)
            .on(
                QApplicationJpaEntity.applicationJpaEntity.grade.eq(QUserJpaEntity.userJpaEntity.grade)
                    .and(QApplicationJpaEntity.applicationJpaEntity.classNum.eq(QUserJpaEntity.userJpaEntity.classNum))
                    .and(QApplicationJpaEntity.applicationJpaEntity.num.eq(QUserJpaEntity.userJpaEntity.num))
            )
            .where(
                QApplicationJpaEntity.applicationJpaEntity.status.eq(status),
                QApplicationJpaEntity.applicationJpaEntity.applicationKind.eq(applicationKind)

            )
            .fetch()
            .map { applicationMapper.toDomain(it) }
}
