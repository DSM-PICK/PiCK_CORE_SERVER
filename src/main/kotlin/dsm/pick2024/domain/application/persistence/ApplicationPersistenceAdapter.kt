package dsm.pick2024.domain.application.persistence

import com.querydsl.jpa.impl.JPAQueryFactory
import dsm.pick2024.domain.application.domain.Application
import dsm.pick2024.domain.application.entity.QApplicationJapEntity
import dsm.pick2024.domain.application.enums.ApplicationKind
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

    override fun existsByUserId(userId: UUID, applicationKind: ApplicationKind) =
        applicationRepository.existsByUserIdAndApplicationKind(userId, applicationKind)

    override fun existsOKByUserId(userId: UUID, applicationKind: ApplicationKind) =
        applicationRepository.existsByStatusAndUserIdAndApplicationKind(Status.OK, userId, applicationKind)

    override fun findByIdAndApplicationKind(id: UUID, applicationKind: ApplicationKind) = applicationRepository.findById(
        id
    ).let { applicationMapper.toDomain(it) }

    override fun deleteByIdAndApplicationKind(applicationId: UUID, applicationKind: ApplicationKind) {
        applicationRepository.deleteByIdAndApplicationKind(applicationId, applicationKind)
    }

    override fun deleteAllByApplicationKind(applicationKind: ApplicationKind) {
        applicationRepository.deleteAllByApplicationKind(applicationKind)
    }

    override fun findAllByApplicationKind(applicationKind: ApplicationKind) =
        applicationRepository.findAllByApplicationKind(applicationKind).map { applicationMapper.toDomain(it) }

    override fun findByUserIdAndStatusAndApplicationKind(id: UUID, applicationKind: ApplicationKind) =
        applicationRepository.findByUserIdAndStatusAndApplicationKind(id, Status.OK, applicationKind).let {
            applicationMapper.toDomain(it)
        }

    override fun save(application: Application) = applicationRepository.save(applicationMapper.toEntity(application))

    override fun findByFloorAndApplicationKind(floor: Int, applicationKind: ApplicationKind) =
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
                ),
                QApplicationJapEntity.applicationJapEntity.applicationKind.eq(applicationKind)
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
                QUserJpaEntity.userJpaEntity.xquareId.`in`(userIds)
            )
            .fetch()

        return applications.map { applicationMapper.toDomain(it) }
    }

    override fun findByGradeAndClassNumAndApplicationKind(
        grade: Int,
        classNum: Int,
        applicationKind: ApplicationKind
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
            QApplicationJapEntity.applicationJapEntity.status.eq(Status.QUIET),
            QApplicationJapEntity.applicationJapEntity.applicationKind.eq(applicationKind)
        )
        .fetch()
        .map { applicationMapper.toDomain(it) }

    override fun findAllByStatusAndApplicationKind(status: Status, applicationKind: ApplicationKind) =
        jpaQueryFactory
            .selectFrom(QApplicationJapEntity.applicationJapEntity)
            .innerJoin(QUserJpaEntity.userJpaEntity)
            .on(
                QApplicationJapEntity.applicationJapEntity.grade.eq(QUserJpaEntity.userJpaEntity.grade)
                    .and(QApplicationJapEntity.applicationJapEntity.classNum.eq(QUserJpaEntity.userJpaEntity.classNum))
                    .and(QApplicationJapEntity.applicationJapEntity.num.eq(QUserJpaEntity.userJpaEntity.num))
            )
            .where(
                QApplicationJapEntity.applicationJapEntity.status.eq(status),
                QApplicationJapEntity.applicationJapEntity.applicationKind.eq(applicationKind)

            )
            .fetch()
            .map { applicationMapper.toDomain(it) }
}
