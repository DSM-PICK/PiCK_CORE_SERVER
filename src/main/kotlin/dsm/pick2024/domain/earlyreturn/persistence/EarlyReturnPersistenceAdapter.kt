package dsm.pick2024.domain.earlyreturn.persistence

import com.querydsl.jpa.impl.JPAQueryFactory
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.earlyreturn.domain.EarlyReturn
import dsm.pick2024.domain.earlyreturn.entity.QEarlyReturnJpaEntity
import dsm.pick2024.domain.earlyreturn.mapper.EarlyReturnMapper
import dsm.pick2024.domain.earlyreturn.persistence.repository.EarlyReturnRepository
import dsm.pick2024.domain.earlyreturn.port.out.EarlyReturnPort
import dsm.pick2024.domain.user.entity.QUserJpaEntity
import org.springframework.stereotype.Component
import java.util.*

@Component
class EarlyReturnPersistenceAdapter(
    private val earlyReturnMapper: EarlyReturnMapper,
    private val earlyReturnRepository: EarlyReturnRepository,
    private val jpaQueryFactory: JPAQueryFactory
) : EarlyReturnPort {
    override fun saveAll(earlyReturn: List<EarlyReturn>) {
        val entities = earlyReturn.map { earlyReturnMapper.toEntity(it) }
        earlyReturnRepository.saveAll(entities)
    }

    override fun save(earlyReturn: EarlyReturn) = earlyReturnRepository.save(earlyReturnMapper.toEntity(earlyReturn))

    override fun existsByUserId(userId: UUID) = earlyReturnRepository.existsByUserId(userId)

    override fun findById(earlyReturnId: UUID) =
        earlyReturnRepository.findById(earlyReturnId).let { earlyReturnMapper.toDomain(it) }

    override fun findByUserId(userId: UUID) =
        earlyReturnRepository.findByUserId(userId).let { earlyReturnMapper.toDomain(it) }

    override fun deleteById(id: UUID) {
        earlyReturnRepository.deleteById(id)
    }

    override fun deleteAll() {
        earlyReturnRepository.deleteAll()
    }

    override fun findByFloor(floor: Int) =
        jpaQueryFactory
            .select(QEarlyReturnJpaEntity.earlyReturnJpaEntity)
            .innerJoin(QUserJpaEntity.userJpaEntity)
            .on(QEarlyReturnJpaEntity.earlyReturnJpaEntity.username.eq(QUserJpaEntity.userJpaEntity.name))
            .where(
                QUserJpaEntity.userJpaEntity.grade.eq(
                    when (floor) {
                        4 -> 1
                        3 -> 2
                        2 -> 3
                        else -> throw IllegalArgumentException("Invalid floor number")
                    }
                ),
                QEarlyReturnJpaEntity.earlyReturnJpaEntity.status.eq(Status.QUIET)
            )
            .fetch()
            .map { earlyReturnMapper.toDomain(it) }

    override fun findByGradeAndClassNum(
        grade: Int,
        classNum: Int
    ) = jpaQueryFactory
        .selectFrom(QEarlyReturnJpaEntity.earlyReturnJpaEntity)
        .innerJoin(QUserJpaEntity.userJpaEntity)
        .on(QEarlyReturnJpaEntity.earlyReturnJpaEntity.username.eq(QUserJpaEntity.userJpaEntity.name))
        .where(
            QEarlyReturnJpaEntity.earlyReturnJpaEntity.grade.eq(grade),
            QEarlyReturnJpaEntity.earlyReturnJpaEntity.classNum.eq(classNum),
            QEarlyReturnJpaEntity.earlyReturnJpaEntity.status.eq(Status.QUIET)
        )
        .fetch()
        .map { earlyReturnMapper.toDomain(it) }

    override fun deleteAll(earlyReturn: List<EarlyReturn>) {
        val entities: List<EarlyReturn> = earlyReturn
        earlyReturnRepository.deleteAll(entities)
    }

    override fun findAll() = earlyReturnRepository.findAll().map { earlyReturnMapper.toDomain(it) }

    override fun findByOKEarlyReturn(userId: UUID) =
        earlyReturnRepository.findByUserIdAndStatus(userId, Status.OK).let {
            earlyReturnMapper.toDomain(it)
        }

    override fun existsOKByUserId(userId: UUID): Boolean {
        val earlyReturn = QEarlyReturnJpaEntity.earlyReturnJpaEntity
        val user = QUserJpaEntity.userJpaEntity

        return jpaQueryFactory.selectFrom(earlyReturn)
            .innerJoin(user).on(earlyReturn.username.eq(user.name))
            .where(user.id.eq(userId), earlyReturn.status.eq(Status.OK))
            .fetchFirst() != null
    }

    override fun findAllByStatus(status: Status) =
        jpaQueryFactory
            .selectFrom(QEarlyReturnJpaEntity.earlyReturnJpaEntity)
            .innerJoin(QUserJpaEntity.userJpaEntity)
            .on(QEarlyReturnJpaEntity.earlyReturnJpaEntity.username.eq(QUserJpaEntity.userJpaEntity.name))
            .where(
                QEarlyReturnJpaEntity.earlyReturnJpaEntity.status.eq(status)
            )
            .orderBy(
                QEarlyReturnJpaEntity.earlyReturnJpaEntity.grade.asc(),
                QEarlyReturnJpaEntity.earlyReturnJpaEntity.classNum.asc(),
                QEarlyReturnJpaEntity.earlyReturnJpaEntity.num.asc()
            )
            .fetch()
            .map { earlyReturnMapper.toDomain(it) }
}
