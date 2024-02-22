package dsm.pick2024.domain.status.persistence.repository

import dsm.pick2024.domain.status.entity.StatusJpaEntity
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository

interface StatusRepository : JpaRepository<StatusJpaEntity, UUID>
