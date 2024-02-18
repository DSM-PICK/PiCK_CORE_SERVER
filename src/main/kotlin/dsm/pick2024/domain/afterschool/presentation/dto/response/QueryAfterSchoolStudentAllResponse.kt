package dsm.pick2024.domain.afterschool.presentation.dto.response

import dsm.pick2024.domain.afterschool.enum.Status

data class QueryAfterSchoolStudentAllResponse(
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val name: String,
    val status: Status
)
