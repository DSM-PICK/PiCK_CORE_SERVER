package dsm.pick2024.domain.devicetoken.port.out

import dsm.pick2024.domain.devicetoken.domain.UserDeviceToken

interface SaveUserDeviceTokenPort {
    fun save(userDeviceToken: UserDeviceToken): UserDeviceToken
}
