package dsm.pick2024.domain.selfstudy.service

import dsm.pick2024.domain.selfstudy.port.`in`.QueryTodaySelfStudyTeacherUseCase
import dsm.pick2024.domain.selfstudy.port.out.FindByTodaySelfStudyTeacherPort
import dsm.pick2024.domain.selfstudy.presentation.dto.response.QueryTodaySelfStudyTeacherResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class QueryTodaySelfStudyTeacherService(
    private val findByTodaySelfStudyTeacherPort: FindByTodaySelfStudyTeacherPort
) : QueryTodaySelfStudyTeacherUseCase {

    @Transactional(readOnly = true)
    override fun queryTodaySelfStudyTeacher(date: LocalDate) =
        findByTodaySelfStudyTeacherPort.findByTodaySelfStudy(date).map {
                it ->
            QueryTodaySelfStudyTeacherResponse(it.floor, it.teacher)
        }
}
