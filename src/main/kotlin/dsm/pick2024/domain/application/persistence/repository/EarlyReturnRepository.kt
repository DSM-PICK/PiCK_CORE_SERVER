package dsm.pick2024.domain.application.persistence.repository

import dsm.pick2024.domain.application.entity.EarlyReturnJpaEntity
import org.springframework.data.repository.Repository
import java.util.*

interface EarlyReturnRepository : Repository<EarlyReturnJpaEntity, UUID> {
    fun existsByUsername(username: String): Boolean

    fun findById(earlyReturnId: UUID): EarlyReturnJpaEntity

    fun deleteById(id: UUID)

    fun saveAll(entity: Iterable<EarlyReturnJpaEntity>)

    fun save(entity: EarlyReturnJpaEntity)

    fun findAll(): List<EarlyReturnJpaEntity>
}
