package dsm.pick2024.domain.devicetoken.persistence

import dsm.pick2024.domain.devicetoken.domain.UserDeviceToken
import dsm.pick2024.domain.devicetoken.mapper.UserDeviceTokenMapper
import dsm.pick2024.domain.devicetoken.persistence.repository.UserDeviceTokenRepository
import dsm.pick2024.domain.devicetoken.port.out.UserDeviceTokenPort
import dsm.pick2024.domain.user.domain.User
import org.springframework.stereotype.Component

@Component
class UserDeviceTokenPersistenceAdapter(
    private val userDeviceTokenRepository: UserDeviceTokenRepository,
    private val userAdminDeviceTokenMapper: UserDeviceTokenMapper
) : UserDeviceTokenPort {
    override fun findAllByUserId(userId: User): List<UserDeviceToken> {
        return userDeviceTokenRepository.findAllByUserId(userId)
            .map { userAdminDeviceTokenMapper.toDomain(it) }
    }
}
