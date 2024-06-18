package dsm.pick2024.domain.selfstudy.service

import dsm.pick2024.domain.selfstudy.port.`in`.QueryMonthSelfStudyTeacherUseCase
import dsm.pick2024.domain.selfstudy.port.out.QuerySelfStudyPort
import dsm.pick2024.domain.selfstudy.presentation.dto.response.QuerySelfStudyTeacherResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Month
import java.time.Year

@Service
class QueryMonthSelfStudyTeacherService(
    private val querySelfStudyPort: QuerySelfStudyPort
) : QueryMonthSelfStudyTeacherUseCase {

    @Transactional(readOnly = true)
    override fun queryMonthSelfStudyTeacher(
        year: Year,
        month: Month
    ) = querySelfStudyPort.findByMonthSelfStudyTeacher(year, month)
        .map {
                it ->
            QuerySelfStudyTeacherResponse(
                floor = it.floor,
                teacher = it.teacherName,
                date = it.date
            )
        }
}
