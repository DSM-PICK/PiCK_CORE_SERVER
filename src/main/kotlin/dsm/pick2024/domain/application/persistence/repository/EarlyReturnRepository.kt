package dsm.pick2024.domain.application.persistence.repository

import dsm.pick2024.domain.application.entity.EarlyReturnJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface EarlyReturnRepository : JpaRepository<EarlyReturnJpaEntity, UUID> {
    fun existsByUsername(username: String): Boolean
}
