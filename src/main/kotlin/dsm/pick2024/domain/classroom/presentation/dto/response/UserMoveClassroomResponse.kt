package dsm.pick2024.domain.classroom.presentation.dto.response

data class UserMoveClassroomResponse(
    val username: String,
    val classroom: String,
    val startPeriod: Int,
    val endPeriod: Int
)
