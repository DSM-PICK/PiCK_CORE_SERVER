package dsm.pick2024.domain.devicetoken.mapper

import dsm.pick2024.domain.devicetoken.domain.UserDeviceToken
import dsm.pick2024.domain.devicetoken.entity.UserDeviceTokenJpaEntity
import dsm.pick2024.domain.devicetoken.exception.EntityStateException
import dsm.pick2024.global.base.GenericMapper
import org.springframework.stereotype.Component

@Component
class UserDeviceTokenMapper :
    GenericMapper<UserDeviceTokenJpaEntity, UserDeviceToken> {
    override fun toEntity(domain: UserDeviceToken) = domain.run {
        UserDeviceTokenJpaEntity(
            id = id,
            userId = userId,
            deviceToken = deviceToken,
            os = os
        )
    }

    override fun toDomain(entity: UserDeviceTokenJpaEntity) = entity.run {
        UserDeviceToken(
            id = id!!,
            userId = userId,
            deviceToken = deviceToken,
            os = os
        )
    }

    fun updateEntity(
        existingEntity: UserDeviceTokenJpaEntity,
        domain: UserDeviceToken
    ): UserDeviceTokenJpaEntity {
        return UserDeviceTokenJpaEntity(
            id = existingEntity.id ?: throw EntityStateException,
            userId = existingEntity.userId,
            deviceToken = existingEntity.deviceToken,
            os = existingEntity.os
        )
    }
}
