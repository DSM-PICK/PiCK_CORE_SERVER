package dsm.pick2024.domain.application.persistence.repository

import dsm.pick2024.domain.application.entity.ApplicationJapEntity
import org.springframework.data.repository.Repository
import java.util.UUID

interface ApplicationRepository : Repository<ApplicationJapEntity, UUID> {
    fun existsByUserId(userId: UUID): Boolean

    fun saveAll(entity: Iterable<ApplicationJapEntity>)

    fun findById(id: UUID): ApplicationJapEntity

    fun deleteById(id: UUID)

    fun save(entity: ApplicationJapEntity)

    fun findAll(): List<ApplicationJapEntity>

    fun findByUserId(userId: UUID): ApplicationJapEntity

    fun deleteAll()
}
