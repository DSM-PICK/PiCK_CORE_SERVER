package dsm.pick2024.domain.user.presentation.dto.request

import dsm.pick2024.domain.devicetoken.enum.OSType

data class UserLoginRequest(
    val accountId: String,
    val password: String,
    val deviceToken: String?,
    val os: OSType?
)
