package dsm.pick2024.domain.selfstudy.port.`in`

import dsm.pick2024.domain.selfstudy.presentation.dto.response.QuerySelfStudyTeacherResponse
import java.time.LocalDate

interface QueryMonthSelfStudyTeacherUseCase {
    fun queryMonthSelfStudyTeacher(date: LocalDate): List<QuerySelfStudyTeacherResponse>
}
