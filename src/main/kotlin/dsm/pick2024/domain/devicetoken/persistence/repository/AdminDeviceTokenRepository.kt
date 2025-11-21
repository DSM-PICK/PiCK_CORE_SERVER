package dsm.pick2024.domain.devicetoken.persistence.repository

import dsm.pick2024.domain.devicetoken.domain.AdminDeviceToken
import org.springframework.data.repository.Repository
import java.util.UUID

interface AdminDeviceTokenRepository : Repository<AdminDeviceToken, UUID> {
    fun findAll(): List<AdminDeviceToken>
}
