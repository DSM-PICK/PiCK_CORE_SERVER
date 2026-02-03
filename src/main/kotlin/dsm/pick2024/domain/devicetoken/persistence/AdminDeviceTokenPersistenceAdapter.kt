package dsm.pick2024.domain.devicetoken.persistence

import dsm.pick2024.domain.devicetoken.domain.AdminDeviceToken
import dsm.pick2024.domain.devicetoken.entity.AdminDeviceTokenJpaEntity
import dsm.pick2024.domain.devicetoken.mapper.AdminDeviceTokenMapper
import dsm.pick2024.domain.devicetoken.persistence.repository.AdminDeviceTokenRepository
import dsm.pick2024.domain.devicetoken.port.out.AdminDeviceTokenPort
import org.springframework.stereotype.Component
import java.util.*

@Component
class AdminDeviceTokenPersistenceAdapter(
    private val adminDeviceTokenRepository: AdminDeviceTokenRepository,
    private val adminDeviceTokenMapper: AdminDeviceTokenMapper
) : AdminDeviceTokenPort {

    override fun findAllByAdminId(adminId: UUID): List<AdminDeviceToken> =
        adminDeviceTokenRepository.findAllByAdminId(adminId)
            .map { adminDeviceTokenMapper.toDomain(it) }

    override fun save(adminDeviceToken: AdminDeviceToken): AdminDeviceToken {
        val token = adminDeviceToken.deviceToken

        val entityToSave = resolveEntity(token, adminDeviceToken)

        return adminDeviceTokenMapper.toDomain(
            adminDeviceTokenRepository.save(entityToSave)
        )
    }

    private fun resolveEntity(token: String, domain: AdminDeviceToken): AdminDeviceTokenJpaEntity {
        val existingEntity = adminDeviceTokenRepository.findByDeviceToken(token)

        return if (existingEntity != null) {
            adminDeviceTokenMapper.updateEntity(existingEntity, domain)
        } else {
            adminDeviceTokenMapper.toEntity(domain)
        }
    }
}
