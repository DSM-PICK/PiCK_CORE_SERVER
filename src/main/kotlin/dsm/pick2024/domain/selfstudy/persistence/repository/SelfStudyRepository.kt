package dsm.pick2024.domain.selfstudy.persistence.repository

import dsm.pick2024.domain.selfstudy.entity.SelfStudyJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import java.util.UUID

interface SelfStudyRepository : JpaRepository<SelfStudyJpaEntity, UUID> {
    fun findByDateAndFloor(date: LocalDate, floor: Int): SelfStudyJpaEntity
}
