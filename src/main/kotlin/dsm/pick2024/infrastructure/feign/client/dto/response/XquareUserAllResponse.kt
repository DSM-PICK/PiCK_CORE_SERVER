package dsm.pick2024.infrastructure.feign.client.dto.response

import java.util.UUID

data class XquareUserAllResponse(
    val id: UUID,
    val name: String,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val club: String
)
