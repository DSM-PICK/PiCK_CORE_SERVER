package dsm.pick2024.domain.devicetoken.persistence.repository

import dsm.pick2024.domain.devicetoken.domain.UserDeviceToken
import dsm.pick2024.domain.devicetoken.entity.UserDeviceTokenJpaEntity
import org.springframework.data.repository.Repository
import java.util.UUID

interface UserDeviceTokenRepository : Repository<UserDeviceToken, UUID> {
    fun findAllByUserId(userId: UUID): List<UserDeviceTokenJpaEntity>
    fun save(userDeviceToken: UserDeviceTokenJpaEntity): UserDeviceTokenJpaEntity
}
