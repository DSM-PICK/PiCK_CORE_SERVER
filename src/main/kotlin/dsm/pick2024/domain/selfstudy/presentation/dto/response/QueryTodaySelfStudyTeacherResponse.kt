package dsm.pick2024.domain.selfstudy.presentation.dto.response

import java.util.UUID

data class QueryTodaySelfStudyTeacherResponse(
    val adminId: UUID,
    val floor: Int?,
    val teacherName: String?
)
