package dsm.pick2024.domain.devicetoken.port.out

import dsm.pick2024.domain.devicetoken.entity.UserDeviceTokenJpaEntity

interface SaveAdminDeviceTokenPort {
    fun save(userDeviceToken: UserDeviceTokenJpaEntity): UserDeviceTokenJpaEntity
}
