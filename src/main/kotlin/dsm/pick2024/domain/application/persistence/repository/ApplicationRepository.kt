package dsm.pick2024.domain.application.persistence.repository

import dsm.pick2024.domain.application.domain.Application
import dsm.pick2024.domain.application.entity.ApplicationJapEntity
import org.springframework.data.repository.Repository
import java.util.UUID

interface ApplicationRepository : Repository<ApplicationJapEntity, UUID> {
    fun existsByUsername(username: String): Boolean

    fun saveAll(entity: Iterable<ApplicationJapEntity>)

    fun findById(id: UUID): ApplicationJapEntity

    fun deleteById(id: UUID)

    fun deleteAll(entity: Iterable<Application>)

    fun save(entity: ApplicationJapEntity)

    fun findAll(): List<ApplicationJapEntity>

    fun findByUsername(username: String): ApplicationJapEntity
}
