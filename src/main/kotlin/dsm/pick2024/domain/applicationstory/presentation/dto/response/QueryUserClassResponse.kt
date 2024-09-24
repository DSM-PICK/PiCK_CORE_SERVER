package dsm.pick2024.domain.applicationstory.presentation.dto.response

import java.util.UUID

data class QueryUserClassResponse(
    val id: UUID,
    val userName: String,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val applicationCnt: Int,
    val earlyReturnCnt: Int
)
