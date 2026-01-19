package dsm.pick2024.domain.devicetoken.persistence.repository

import dsm.pick2024.domain.devicetoken.entity.UserDeviceTokenJpaEntity
import java.util.UUID

interface UserDeviceTokenRepository {
    fun findAllByUserId(userId: UUID): List<UserDeviceTokenJpaEntity>
    fun save(userDeviceToken: UserDeviceTokenJpaEntity): UserDeviceTokenJpaEntity
}
