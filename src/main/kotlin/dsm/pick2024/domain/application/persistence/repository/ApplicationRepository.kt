package dsm.pick2024.domain.application.persistence.repository

import dsm.pick2024.domain.application.entity.ApplicationJapEntity
import dsm.pick2024.domain.application.enums.ApplicationKind
import dsm.pick2024.domain.application.enums.Status
import org.springframework.data.repository.Repository
import java.util.UUID

interface ApplicationRepository : Repository<ApplicationJapEntity, UUID> {
    fun existsByUserId(userId: UUID): Boolean

    fun existsByStatusAndUserIdAndApplicationKind(
        status: Status,
        userId: UUID,
        applicationKind: ApplicationKind
    ): Boolean

    fun saveAll(entity: Iterable<ApplicationJapEntity>)

    fun findById(id: UUID): ApplicationJapEntity

    fun deleteByIdAndApplicationKind(id: UUID, applicationKind: ApplicationKind)

    fun save(entity: ApplicationJapEntity)

    fun findAllByApplicationKind(applicationKind: ApplicationKind): List<ApplicationJapEntity>

    fun findAllByStatus(status: Status): List<ApplicationJapEntity>

    fun findByUserId(userId: UUID): ApplicationJapEntity

    fun deleteAll()

    fun deleteAllByApplicationKind(applicationKind: ApplicationKind)

    fun findByUserIdAndStatusAndApplicationKind(
        id: UUID,
        status: Status,
        applicationKind: ApplicationKind
    ): ApplicationJapEntity
}
