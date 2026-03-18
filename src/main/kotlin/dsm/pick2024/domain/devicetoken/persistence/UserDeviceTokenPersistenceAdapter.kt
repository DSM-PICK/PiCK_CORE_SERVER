package dsm.pick2024.domain.devicetoken.persistence

import dsm.pick2024.domain.devicetoken.domain.UserDeviceToken
import dsm.pick2024.domain.devicetoken.mapper.UserDeviceTokenMapper
import dsm.pick2024.domain.devicetoken.persistence.repository.UserDeviceTokenRepository
import dsm.pick2024.domain.devicetoken.port.out.UserDeviceTokenPort
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserDeviceTokenPersistenceAdapter(
    private val userDeviceTokenRepository: UserDeviceTokenRepository,
    private val userDeviceTokenMapper: UserDeviceTokenMapper
) : UserDeviceTokenPort {
    override fun findAllByUserId(userId: UUID): List<UserDeviceToken> =
        userDeviceTokenRepository.findAllByUserId(userId)
            .map { userDeviceTokenMapper.toDomain(it) }

    override fun findByUserIds(userIds: List<UUID>): List<UserDeviceToken> {
        if (userIds.isEmpty()) return emptyList()
        return userDeviceTokenRepository.findAllByUserIdIn(userIds)
            .map { userDeviceTokenMapper.toDomain(it) }
    }

    override fun findByDeviceToken(deviceToken: String): UserDeviceToken? =
        userDeviceTokenRepository.findByDeviceToken(deviceToken)
            ?.let { userDeviceTokenMapper.toDomain(it) }

    override fun save(userDeviceToken: UserDeviceToken): UserDeviceToken =
        userDeviceTokenMapper.toDomain(
            userDeviceTokenRepository.save(userDeviceTokenMapper.toEntity(userDeviceToken))
        )
}
