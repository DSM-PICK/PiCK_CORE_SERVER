package dsm.pick2024.domain.fcm.dto.request

import dsm.pick2024.domain.user.domain.User

data class FcmRequest(
    val users: List<User>,
    val title: String,
    val body: String

)
