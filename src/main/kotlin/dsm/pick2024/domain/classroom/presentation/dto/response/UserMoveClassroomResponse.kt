package dsm.pick2024.domain.classroom.presentation.dto.response

data class UserMoveClassroomResponse(
    val userName: String,
    val classroom: String,
    val start: Int,
    val end: Int
)
