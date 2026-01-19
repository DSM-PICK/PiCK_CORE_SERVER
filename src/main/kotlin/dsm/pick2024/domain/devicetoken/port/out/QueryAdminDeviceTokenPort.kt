package dsm.pick2024.domain.devicetoken.port.out

import dsm.pick2024.domain.devicetoken.entity.UserDeviceTokenJpaEntity
import java.util.*

interface QueryAdminDeviceTokenPort {
    fun findAllByUserId(userId: UUID): List<UserDeviceTokenJpaEntity>
}
