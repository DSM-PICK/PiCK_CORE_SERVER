package dsm.pick2024.domain.selfstudy.presentation.dto.response

import java.time.LocalDate

data class QuerySelfStudyTeacherResponse(
    val floor: Int,
    val teacher: String,
    val date: LocalDate
)
