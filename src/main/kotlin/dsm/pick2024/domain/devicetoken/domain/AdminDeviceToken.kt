package dsm.pick2024.domain.devicetoken.domain

import dsm.pick2024.domain.admin.domain.Admin
import dsm.pick2024.domain.devicetoken.enum.OSType
import dsm.pick2024.global.annotation.Aggregate
import java.util.UUID

@Aggregate
data class AdminDeviceToken(
    val id: UUID = UUID(0, 0),
    val adminId: Admin,
    val deviceToken: String,
    val os: OSType
)
