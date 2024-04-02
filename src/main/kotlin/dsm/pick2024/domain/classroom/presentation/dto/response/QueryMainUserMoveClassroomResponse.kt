package dsm.pick2024.domain.classroom.presentation.dto.response

import dsm.pick2024.domain.main.Main

data class QueryMainUserMoveClassroomResponse(
    val username: String,
    val classroom: String,
    val type: Main
)
