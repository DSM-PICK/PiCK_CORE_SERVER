package dsm.pick2024.domain.selfstudy.service

import dsm.pick2024.domain.selfstudy.port.`in`.QueryDateSelfStudyUseCase
import dsm.pick2024.domain.selfstudy.port.out.QuerySelfStudyPort
import dsm.pick2024.domain.selfstudy.presentation.dto.response.QuerySelfStudyTeacherResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class QuerySelfStudyTeacherService(
    private val querySelfStudyPort: QuerySelfStudyPort
) : QueryDateSelfStudyUseCase {

    @Transactional(readOnly = true)
    override fun queryDateSelfStudy(date: LocalDate) =

        querySelfStudyPort.findByDateList(date)
            .map {
                    it ->
                QuerySelfStudyTeacherResponse(
                    floor = it!!.floor,
                    teacher = it.teacherName,
                    date = it.date
                )
            }
}
