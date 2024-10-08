package dsm.pick2024.domain.classroom.presentation.dto.response

import dsm.pick2024.domain.main.Main

data class QueryMainUserMoveClassroomResponse(
    val userName: String,
    val classroom: String,
    val start: String,
    val end: String,
    val type: Main
)
