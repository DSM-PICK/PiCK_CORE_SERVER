package dsm.pick2024.domain.application.persistence.repository

import dsm.pick2024.domain.application.entity.ApplicationJpaEntity
import dsm.pick2024.domain.application.enums.ApplicationKind
import dsm.pick2024.domain.application.enums.Status
import org.springframework.data.repository.Repository
import java.util.UUID

interface ApplicationRepository : Repository<ApplicationJpaEntity, UUID> {
    fun existsByUserId(userId: UUID): Boolean

    fun existsByUserIdAndApplicationKind(
        userId: UUID,
        applicationKind: ApplicationKind
    ): Boolean

    fun existsByStatusAndUserIdAndApplicationKind(
        status: Status,
        userId: UUID,
        applicationKind: ApplicationKind
    ): Boolean

    fun saveAll(entity: Iterable<ApplicationJpaEntity>)

    fun findById(id: UUID): ApplicationJpaEntity?

    fun findByUserId(userId: UUID): ApplicationJpaEntity?

    fun deleteByIdAndApplicationKind(id: UUID, applicationKind: ApplicationKind)

    fun save(entity: ApplicationJpaEntity)

    fun findAllByApplicationKind(applicationKind: ApplicationKind): List<ApplicationJpaEntity>

    fun findAllByStatus(status: Status): List<ApplicationJpaEntity>

    fun deleteAll()

    fun deleteAllByApplicationKind(applicationKind: ApplicationKind)

    fun findByUserIdAndStatusAndApplicationKind(
        id: UUID,
        status: Status,
        applicationKind: ApplicationKind
    ): ApplicationJpaEntity?
}
