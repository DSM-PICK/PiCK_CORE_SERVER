package dsm.pick2024.infrastructure.feign.xquare.dto.response

import dsm.pick2024.domain.user.entity.enums.Role
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
    val profileImageUrl: String? = null,
    val birthDay: LocalDate
)
