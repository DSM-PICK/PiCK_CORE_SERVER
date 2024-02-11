package dsm.pick2024.domain.selfstudy.service

import dsm.pick2024.domain.selfstudy.port.`in`.QueryMonthSelfStudyTeacherUseCase
import dsm.pick2024.domain.selfstudy.port.out.FindByMonthSelfStudyTeacherPort
import dsm.pick2024.domain.selfstudy.presentation.dto.response.QuerySelfStudyTeacherResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class QuerySelfStudyTeacherService (
    private val findByMonthSelfStudyTeacherPort: FindByMonthSelfStudyTeacherPort
): QueryMonthSelfStudyTeacherUseCase {

    @Transactional(readOnly = true)
    override fun queryMonthSelfStudyTeacher(date: LocalDate) =
        findByMonthSelfStudyTeacherPort.findByMonthSelfStudyTeacher(date)
            .map {
                it ->
                QuerySelfStudyTeacherResponse(
                    it.floor,
                    it.teacher,
                    it.date
                )
            }
}
