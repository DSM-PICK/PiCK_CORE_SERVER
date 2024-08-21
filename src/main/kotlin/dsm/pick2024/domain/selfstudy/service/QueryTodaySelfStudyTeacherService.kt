package dsm.pick2024.domain.selfstudy.service

import dsm.pick2024.domain.selfstudy.port.`in`.QueryTodaySelfStudyTeacherUseCase
import dsm.pick2024.domain.selfstudy.port.out.QuerySelfStudyPort
import dsm.pick2024.domain.selfstudy.presentation.dto.response.QueryTodaySelfStudyTeacherResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class QueryTodaySelfStudyTeacherService(
    private val querySelfStudyPort: QuerySelfStudyPort
) : QueryTodaySelfStudyTeacherUseCase {

    @Transactional(readOnly = true)
    override fun queryTodaySelfStudyTeacher(date: LocalDate) =

        querySelfStudyPort.findByDaySelfStudy(date).map {
                it ->
            QueryTodaySelfStudyTeacherResponse(it.floor, it.teacherName)
        }.sortedWith(compareBy { it.floor })
}
