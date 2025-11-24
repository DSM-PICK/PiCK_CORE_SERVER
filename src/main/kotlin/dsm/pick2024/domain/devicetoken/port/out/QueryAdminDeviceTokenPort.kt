package dsm.pick2024.domain.devicetoken.port.out

import dsm.pick2024.domain.admin.domain.Admin
import dsm.pick2024.domain.devicetoken.domain.AdminDeviceToken

interface QueryAdminDeviceTokenPort {
    fun findAllByAdminId(adminId: Admin): List<AdminDeviceToken>
}
