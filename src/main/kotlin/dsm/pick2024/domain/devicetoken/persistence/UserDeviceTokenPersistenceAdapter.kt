package dsm.pick2024.domain.devicetoken.persistence

import dsm.pick2024.domain.devicetoken.domain.UserDeviceToken
import dsm.pick2024.domain.devicetoken.mapper.UserDeviceTokenMapper
import dsm.pick2024.domain.devicetoken.persistence.repository.UserDeviceTokenRepository
import dsm.pick2024.domain.devicetoken.port.out.UserDeviceTokenPort
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserDeviceTokenPersistenceAdapter(
    private val userDeviceTokenRepository: UserDeviceTokenRepository,
    private val userAdminDeviceTokenMapper: UserDeviceTokenMapper,
    private val userDeviceTokenMapper: UserDeviceTokenMapper
) : UserDeviceTokenPort {
    override fun findAllByUserId(userId: UUID): List<UserDeviceToken> {
        return userDeviceTokenRepository.findAllByUserId(userId)
            .map { userAdminDeviceTokenMapper.toDomain(it) }
    }

    override fun save(userDeviceToken: UserDeviceToken): UserDeviceToken {
        val entity = userDeviceTokenMapper.toEntity(userDeviceToken)
        val savedEntity = userDeviceTokenRepository.save(entity)
        return userDeviceTokenMapper.toDomain(savedEntity)
    }
}
