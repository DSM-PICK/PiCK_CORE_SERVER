package dsm.pick2024.domain.status.persistence

import com.querydsl.jpa.impl.JPAQueryFactory
import dsm.pick2024.domain.status.domain.Status
import dsm.pick2024.domain.status.entity.QStatusJpaEntity
import dsm.pick2024.domain.status.entity.StatusJpaEntity
import dsm.pick2024.domain.status.mapper.StatusMapper
import dsm.pick2024.domain.status.persistence.repository.StatusRepository
import dsm.pick2024.domain.status.port.out.StatusPort
import org.springframework.stereotype.Component

@Component
class StatusPersistenceAdapter(
    private val statusRepository: StatusRepository,
    private val jpaQueryFactory: JPAQueryFactory,
    private val statusMapper: StatusMapper

) : StatusPort {
    override fun saveAll(statuses: MutableList<StatusJpaEntity>) {
        statusRepository.saveAll(statuses)
    }

    override fun findByGradeAndClassNum(grade: Int, classNum: Int): List<Status> {
        return jpaQueryFactory
            .selectFrom(QStatusJpaEntity.statusJpaEntity)
            .where(
                QStatusJpaEntity.statusJpaEntity.grade.eq(grade),
                QStatusJpaEntity.statusJpaEntity.classNum.eq(classNum)
            )
            .orderBy(QStatusJpaEntity.statusJpaEntity.num.asc())
            .fetch()
            .map { statusMapper.toDomain(it) }
    }
}
