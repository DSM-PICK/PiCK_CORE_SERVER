package dsm.pick2024.domain.admin.presentation.dto.request

import dsm.pick2024.domain.devicetoken.enum.OSType

data class AdminLoginRequest(
    val adminId: String,
    val password: String,
    val deviceToken: String?,
    val os: OSType?
)
