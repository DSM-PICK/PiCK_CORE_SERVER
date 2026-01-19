package dsm.pick2024.domain.devicetoken.port.out

import dsm.pick2024.domain.devicetoken.domain.AdminDeviceToken
import java.util.*

interface QueryAdminDeviceTokenPort {
    fun findAllByAdminId(adminId: UUID): List<AdminDeviceToken>
}
