package dsm.pick2024.domain.selfstudy.service

import dsm.pick2024.domain.selfstudy.port.`in`.QueryTodaySelfStudyTeacherUseCase
import dsm.pick2024.domain.selfstudy.port.`in`.SelfStudyFinderUseCase
import dsm.pick2024.domain.selfstudy.presentation.dto.response.QueryTodaySelfStudyTeacherResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class QueryTodaySelfStudyTeacherService(
    private val selfStudyFinderUseCase: SelfStudyFinderUseCase
) : QueryTodaySelfStudyTeacherUseCase {

    @Transactional(readOnly = true)
    override fun queryTodaySelfStudyTeacher(date: LocalDate) =

        selfStudyFinderUseCase.findByDaySelfStudyOrThrow(date).map {
            QueryTodaySelfStudyTeacherResponse(it.floor, it.teacherName)
        }.sortedWith(compareBy { it.floor })
}
