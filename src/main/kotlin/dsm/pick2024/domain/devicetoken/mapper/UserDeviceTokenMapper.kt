package dsm.pick2024.domain.devicetoken.mapper

import dsm.pick2024.domain.devicetoken.domain.UserDeviceToken
import dsm.pick2024.domain.devicetoken.entity.UserDeviceTokenJpaEntity
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
}
