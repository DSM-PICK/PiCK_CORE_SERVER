package dsm.pick2024.domain.selfstudy.presentation.dto.request

import java.time.LocalDate

data class ChangeSelfStudyTeacherRequest (
    val teacher: String,
    val date: LocalDate
)
