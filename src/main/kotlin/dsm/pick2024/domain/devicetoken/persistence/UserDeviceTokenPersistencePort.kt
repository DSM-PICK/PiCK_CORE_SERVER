package dsm.pick2024.domain.devicetoken.persistence

import dsm.pick2024.domain.devicetoken.domain.UserDeviceToken
import dsm.pick2024.domain.devicetoken.entity.UserDeviceTokenJpaEntity
import dsm.pick2024.domain.devicetoken.mapper.UserDeviceTokenMapper
import dsm.pick2024.domain.devicetoken.persistence.repository.UserDeviceTokenRepository
import dsm.pick2024.domain.devicetoken.port.out.UserDeviceTokenPort
import java.util.*

class UserDeviceTokenPersistencePort(
    private val userDeviceTokenRepository: UserDeviceTokenRepository,
    private val userDeviceTokenMapper: UserDeviceTokenMapper
) : UserDeviceTokenPort {
    override fun findAllByUserId(userId: UUID): List<UserDeviceToken> =
        userDeviceTokenRepository.findAllByUserId(userId)
            .map { userDeviceTokenMapper.toDomain(it) }

    override fun save(userDeviceToken: UserDeviceToken): UserDeviceToken {
        val token = userDeviceToken.deviceToken

        val entityToSave = resolveEntity(token, userDeviceToken)

        return userDeviceTokenMapper.toDomain(
            userDeviceTokenRepository.save(entityToSave)
        )
    }

    private fun resolveEntity(token: String, domain: UserDeviceToken): UserDeviceTokenJpaEntity {
        val existingEntity = userDeviceTokenRepository.findByDeviceToken(token)

        return if (existingEntity != null) {
            userDeviceTokenMapper.updateEntity(existingEntity, domain)
        } else {
            userDeviceTokenMapper.toEntity(domain)
        }
    }
}
