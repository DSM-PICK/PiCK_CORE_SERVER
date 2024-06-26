package dsm.pick2024.domain.earlyreturn.persistence.repository

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.earlyreturn.entity.EarlyReturnJpaEntity
import org.springframework.data.repository.Repository
import java.util.UUID

interface EarlyReturnRepository : Repository<EarlyReturnJpaEntity, UUID> {
    fun existsByUserId(userId: UUID): Boolean

    fun findById(earlyReturnId: UUID): EarlyReturnJpaEntity

    fun deleteById(id: UUID)

    fun saveAll(entity: Iterable<EarlyReturnJpaEntity>)

    fun save(entity: EarlyReturnJpaEntity)

    fun findAll(): List<EarlyReturnJpaEntity>

    fun findByUserId(userId: UUID): EarlyReturnJpaEntity

    fun deleteAll()

    fun findByUserIdAndStatus(
        userID: UUID,
        status: Status
    ): EarlyReturnJpaEntity
}
