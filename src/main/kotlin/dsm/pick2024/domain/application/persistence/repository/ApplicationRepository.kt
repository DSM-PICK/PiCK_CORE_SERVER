package dsm.pick2024.domain.application.persistence.repository

import dsm.pick2024.domain.application.entity.ApplicationJapEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ApplicationRepository : JpaRepository<ApplicationJapEntity, UUID> {
    fun existsByUsername(username: String): Boolean
}
