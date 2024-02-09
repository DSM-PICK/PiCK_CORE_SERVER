package dsm.pick2024.domain.selfstudy.port.`in`

import dsm.pick2024.domain.selfstudy.presentation.dto.response.QueryTodaySelfStudyTeacherResponse

interface QueryTodaySelfStudyTeacherUseCase {
    fun queryTodaySelfStudyTeacher(): List<QueryTodaySelfStudyTeacherResponse>
}
