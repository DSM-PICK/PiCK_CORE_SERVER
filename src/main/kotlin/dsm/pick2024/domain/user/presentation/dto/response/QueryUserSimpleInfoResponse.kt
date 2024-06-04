package dsm.pick2024.domain.user.presentation.dto.response

import dsm.pick2024.domain.afterschool.domain.AfterSchoolStudent

data class QueryUserSimpleInfoResponse(
    val name: String,
    val grade: Int,
    val classNum: Int,
    val num: Int
)

