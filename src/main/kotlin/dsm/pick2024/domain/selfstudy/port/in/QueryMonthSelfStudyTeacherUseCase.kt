package dsm.pick2024.domain.selfstudy.port.`in`

import dsm.pick2024.domain.selfstudy.presentation.dto.response.QuerySelfStudyTeacherResponse
import java.time.LocalDate
import java.time.Month
import java.time.Year

interface QueryMonthSelfStudyTeacherUseCase {
    fun queryMonthSelfStudyTeacher(year: Year, month: Month): List<QuerySelfStudyTeacherResponse>
}
