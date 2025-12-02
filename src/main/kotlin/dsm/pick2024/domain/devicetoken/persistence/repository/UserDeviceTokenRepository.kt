package dsm.pick2024.domain.devicetoken.persistence.repository

import dsm.pick2024.domain.devicetoken.entity.UserDeviceTokenJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserDeviceTokenRepository : JpaRepository<UserDeviceTokenJpaEntity, UUID> {
    fun findAllByUserId(userId: UUID): List<UserDeviceTokenJpaEntity>
    fun save(userDeviceToken: UserDeviceTokenJpaEntity): UserDeviceTokenJpaEntity
}
