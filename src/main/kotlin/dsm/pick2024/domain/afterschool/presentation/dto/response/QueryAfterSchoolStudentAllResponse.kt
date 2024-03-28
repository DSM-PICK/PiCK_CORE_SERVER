package dsm.pick2024.domain.afterschool.presentation.dto.response

import dsm.pick2024.domain.afterschool.enums.Status
import java.util.UUID

data class QueryAfterSchoolStudentAllResponse(
    val id: UUID,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val name: String,
    val status1: Status,
    val status2: Status,
    val status3: Status
)
