package dsm.pick2024.domain.selfstudy.port.`in`

import dsm.pick2024.domain.selfstudy.presentation.dto.response.QueryTodaySelfStudyTeacherResponse
import java.time.LocalDate

interface QueryTodaySelfStudyTeacherUseCase {
    fun queryTodaySelfStudyTeacher(date: LocalDate): List<QueryTodaySelfStudyTeacherResponse>
}
