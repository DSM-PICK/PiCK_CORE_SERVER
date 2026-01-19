package dsm.pick2024.domain.devicetoken.domain

import dsm.pick2024.domain.devicetoken.enum.OSType
import dsm.pick2024.global.annotation.Aggregate
import java.util.UUID

@Aggregate
data class UserDeviceToken(
    val id: UUID = UUID(0, 0),
    val userId: UUID,
    val deviceToken: String,
    val os: OSType
)
