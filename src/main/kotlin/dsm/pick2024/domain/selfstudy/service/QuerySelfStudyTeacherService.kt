package dsm.pick2024.domain.selfstudy.service

import dsm.pick2024.domain.selfstudy.port.`in`.QueryDateSelfStudyUseCase
import dsm.pick2024.domain.selfstudy.port.`in`.SelfStudyFinderUseCase
import dsm.pick2024.domain.selfstudy.presentation.dto.response.QuerySelfStudyTeacherResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class QuerySelfStudyTeacherService(
    private val selfStudyFinderUseCase: SelfStudyFinderUseCase
) : QueryDateSelfStudyUseCase {

    @Transactional(readOnly = true)
    override fun queryDateSelfStudy(date: LocalDate) =

        selfStudyFinderUseCase.findByDateListOrThrow(date)
            .map {
                    it ->
                QuerySelfStudyTeacherResponse(
                    floor = it!!.floor,
                    teacher = it.teacherName,
                    date = it.date
                )
            }
}
