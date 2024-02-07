package dsm.pick2024.domain.application.mapper

import dsm.pick2024.domain.application.domain.EarlyReturn
import dsm.pick2024.domain.application.entity.EarlyReturnJpaEntity
import org.springframework.stereotype.Component

@Component
class EarlyReturnMapper {
    fun toEntity(domain: EarlyReturn) = domain.run {
        EarlyReturnJpaEntity(
            id = id,
            reason = reason,
            startTime = startTime,
            username = username,
            status = status
        )
    }

    fun toDomain(entity: EarlyReturnJpaEntity) = entity.run {
        EarlyReturn(
            id = id,
            reason = reason,
            startTime = startTime,
            username = username,
            status = status
        )
    }
}
