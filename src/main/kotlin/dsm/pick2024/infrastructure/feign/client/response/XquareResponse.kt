package dsm.pick2024.infrastructure.feign.client.response

import java.util.*

data class XquareResponse(
    val id: UUID,
    val accountId: String,
    val password: String,
    val name: String,
    val grade: Int?,
    val classNum: Int?,
    val num: Int?,
    val userRole: String
)
