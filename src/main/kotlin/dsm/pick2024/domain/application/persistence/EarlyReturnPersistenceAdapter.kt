package dsm.pick2024.domain.application.persistence

import dsm.pick2024.domain.application.domain.EarlyReturn
import dsm.pick2024.domain.application.mapper.EarlyReturnMapper
import dsm.pick2024.domain.application.persistence.repository.EarlyReturnRepository
import dsm.pick2024.domain.application.port.out.EarlyReturnPort
import org.springframework.stereotype.Component
import java.util.*

@Component
class EarlyReturnPersistenceAdapter(
    private val earlyReturnMapper: EarlyReturnMapper,
    private val earlyReturnRepository: EarlyReturnRepository
) : EarlyReturnPort {

    override fun save(earlyReturn: EarlyReturn) {
        earlyReturnRepository.save(earlyReturnMapper.toEntity(earlyReturn))
    }

    override fun existsByUsername(username: String) =
        earlyReturnRepository.existsByUsername(username)

    override fun findByUsername(username: String) =
        earlyReturnRepository.findByUsername(username).let { earlyReturnMapper.toDomain(it) }
}
