package dsm.pick2024.domain.application.persistence.repository

import dsm.pick2024.domain.application.entity.ApplicationJapEntity
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository

interface ApplicationRepository : JpaRepository<ApplicationJapEntity, UUID>
