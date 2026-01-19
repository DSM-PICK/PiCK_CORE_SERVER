package dsm.pick2024.domain.devicetoken.port.out

import dsm.pick2024.domain.devicetoken.domain.AdminDeviceToken

interface SaveAdminDeviceTokenPort {
    fun save(adminDeviceToken: AdminDeviceToken): AdminDeviceToken
}
