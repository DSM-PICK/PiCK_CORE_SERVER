package dsm.pick2024.domain.selfstudy.persistence.repository

import dsm.pick2024.domain.selfstudy.entity.SelfStudyJpaEntity
import org.springframework.data.repository.Repository
import java.time.LocalDate
import java.util.UUID

interface SelfStudyRepository : Repository<SelfStudyJpaEntity, UUID> {

    fun save(entity: SelfStudyJpaEntity): SelfStudyJpaEntity

    fun saveAll(entity: Iterable<SelfStudyJpaEntity>)

    fun findByDateAndFloor(date: LocalDate, floor: Int): SelfStudyJpaEntity

    fun findByDate(date: LocalDate): SelfStudyJpaEntity
}
