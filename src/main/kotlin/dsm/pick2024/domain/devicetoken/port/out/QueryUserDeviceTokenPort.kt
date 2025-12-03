package dsm.pick2024.domain.devicetoken.port.out

import dsm.pick2024.domain.devicetoken.domain.UserDeviceToken
import dsm.pick2024.domain.user.domain.User

interface QueryUserDeviceTokenPort {
    fun findAllByUserId(userId: User): List<UserDeviceToken>
}
