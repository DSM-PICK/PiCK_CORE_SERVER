package dsm.pick2024.domain.devicetoken.persistence.repository

import dsm.pick2024.domain.devicetoken.entity.AdminDeviceTokenJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface AdminDeviceTokenRepository : JpaRepository<AdminDeviceTokenJpaEntity, UUID> {
    fun findAllByAdminId(adminId: UUID): List<AdminDeviceTokenJpaEntity>
}
