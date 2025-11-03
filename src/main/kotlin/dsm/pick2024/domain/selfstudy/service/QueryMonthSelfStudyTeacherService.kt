package dsm.pick2024.domain.selfstudy.service

import dsm.pick2024.domain.selfstudy.port.`in`.QueryMonthSelfStudyTeacherUseCase
import dsm.pick2024.domain.selfstudy.port.`in`.SelfStudyFinderUseCase
import dsm.pick2024.domain.selfstudy.presentation.dto.response.QuerySelfStudyTeacherResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Month
import java.time.Year

@Service
class QueryMonthSelfStudyTeacherService(
    private val selfStudyFinderUseCase: SelfStudyFinderUseCase
) : QueryMonthSelfStudyTeacherUseCase {

    @Transactional(readOnly = true)
    override fun queryMonthSelfStudyTeacher(
        year: Year,
        month: Month
    ) = selfStudyFinderUseCase.findByMonthSelfStudyTeacherOrThrow(year, month)
        .map {
            QuerySelfStudyTeacherResponse(
                floor = it.floor,
                teacher = it.teacherName,
                date = it.date
            )
        }
}
