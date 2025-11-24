package dsm.pick2024.domain.devicetoken.persistence.repository

import dsm.pick2024.domain.devicetoken.domain.UserDeviceToken
import dsm.pick2024.domain.devicetoken.entity.UserDeviceTokenJpaEntity
import dsm.pick2024.domain.user.domain.User
import org.springframework.data.repository.Repository
import java.util.*

interface UserDeviceTokenRepository : Repository<UserDeviceToken, UUID> {
    fun findAllByUserId(userId: User): List<UserDeviceTokenJpaEntity>
}
