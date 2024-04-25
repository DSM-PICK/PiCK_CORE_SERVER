package dsm.pick2024.infrastructure.feign.client.dto.response

import java.time.LocalDate
import java.util.*

data class XquareResponse(
    val id: UUID,
    val accountId: String,
    val password: String,
    val name: String,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val userRole: Role,
    val clubName: String? = null,
    val birthDay: LocalDate
)
