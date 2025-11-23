package dsm.pick2024.domain.devicetoken.persistence.repository

import dsm.pick2024.domain.devicetoken.domain.AdminDeviceToken
import dsm.pick2024.domain.devicetoken.entity.AdminDeviceTokenJpaEntity
import org.springframework.data.repository.Repository
import java.util.UUID

interface AdminDeviceTokenRepository : Repository<AdminDeviceToken, UUID> {
    fun findAllByAdminId(adminId: UUID): List<AdminDeviceTokenJpaEntity>
}
