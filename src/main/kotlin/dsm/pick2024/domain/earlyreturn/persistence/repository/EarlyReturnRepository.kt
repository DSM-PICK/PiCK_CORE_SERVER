package dsm.pick2024.domain.earlyreturn.persistence.repository

import dsm.pick2024.domain.earlyreturn.domain.EarlyReturn
import dsm.pick2024.domain.earlyreturn.entity.EarlyReturnJpaEntity
import org.springframework.data.repository.Repository
import java.util.*

interface EarlyReturnRepository : Repository<EarlyReturnJpaEntity, UUID> {
    fun existsByUsername(username: String): Boolean

    fun findById(earlyReturnId: UUID): EarlyReturnJpaEntity

    fun deleteById(id: UUID)

    fun deleteAll(entity: Iterable<EarlyReturn>)

    fun saveAll(entity: Iterable<EarlyReturnJpaEntity>)

    fun save(entity: EarlyReturnJpaEntity)

    fun findAll(): List<EarlyReturnJpaEntity>

    fun findByUsername(username: String): EarlyReturnJpaEntity
}
