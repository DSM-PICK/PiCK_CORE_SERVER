package dsm.pick2024.domain.devicetoken.port.out

import dsm.pick2024.domain.devicetoken.domain.UserDeviceToken
import java.util.UUID

interface QueryUserDeviceTokenPort {
    fun findAllByUserId(userId: UUID): List<UserDeviceToken>
}
