package dsm.pick2024.domain.applicationstory.persistence.repository

import dsm.pick2024.domain.applicationstory.entity.ApplicationStoryJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ApplicationStoryRepository : JpaRepository<ApplicationStoryJpaEntity, UUID> {
    fun findByUserId(userId: UUID): ApplicationStoryJpaEntity?
}
