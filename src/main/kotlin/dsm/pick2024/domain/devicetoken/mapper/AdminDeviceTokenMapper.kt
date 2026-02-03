package dsm.pick2024.domain.devicetoken.mapper

import dsm.pick2024.domain.devicetoken.domain.AdminDeviceToken
import dsm.pick2024.domain.devicetoken.entity.AdminDeviceTokenJpaEntity
import dsm.pick2024.domain.devicetoken.exception.EntityStateException
import dsm.pick2024.global.base.GenericMapper
import org.springframework.stereotype.Component

@Component
class AdminDeviceTokenMapper :
    GenericMapper<AdminDeviceTokenJpaEntity, AdminDeviceToken> {
    override fun toEntity(domain: AdminDeviceToken) = domain.run {
        AdminDeviceTokenJpaEntity(
            id = id,
            adminId = adminId,
            deviceToken = deviceToken,
            os = os
        )
    }

    override fun toDomain(entity: AdminDeviceTokenJpaEntity) = entity.run {
        AdminDeviceToken(
            id = id!!,
            adminId = adminId,
            deviceToken = deviceToken,
            os = os
        )
    }

    fun updateEntity(
        existingEntity: AdminDeviceTokenJpaEntity,
        domain: AdminDeviceToken
    ): AdminDeviceTokenJpaEntity {
        return AdminDeviceTokenJpaEntity(
            id = existingEntity.id ?: throw EntityStateException,
            adminId = existingEntity.adminId,
            deviceToken = existingEntity.deviceToken,
            os = existingEntity.os
        )
    }
}
