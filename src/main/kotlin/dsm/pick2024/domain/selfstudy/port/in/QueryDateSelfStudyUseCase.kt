package dsm.pick2024.domain.selfstudy.port.`in`

import dsm.pick2024.domain.selfstudy.presentation.dto.response.QuerySelfStudyTeacherResponse
import java.time.LocalDate

interface QueryDateSelfStudyUseCase {
    fun queryDateSelfStudy(date: LocalDate): List<QuerySelfStudyTeacherResponse>?
}
