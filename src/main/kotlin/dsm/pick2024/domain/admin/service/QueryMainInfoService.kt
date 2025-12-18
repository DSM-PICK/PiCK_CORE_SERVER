package dsm.pick2024.domain.admin.service

import dsm.pick2024.domain.admin.port.`in`.AdminFacadeUseCase
import dsm.pick2024.domain.admin.port.`in`.QueryMainInfoUseCase
import dsm.pick2024.domain.admin.presentation.dto.response.QueryMainInfoResponse
import dsm.pick2024.domain.selfstudy.port.out.QuerySelfStudyPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class QueryMainInfoService(
    private val adminFacadeUseCase: AdminFacadeUseCase,
    private val querySelfStudyPort: QuerySelfStudyPort
) : QueryMainInfoUseCase {

    @Transactional(readOnly = true)
    override fun execute(): QueryMainInfoResponse {
        val admin = adminFacadeUseCase.currentAdmin()

        val todaySelfStudy = querySelfStudyPort
            .findByTeacherNameAndDate(admin.name, LocalDate.now())

        return QueryMainInfoResponse(
            selfStudyFloor = todaySelfStudy?.floor ?: 0,
            grade = admin.grade ?: 0,
            classNum = admin.classNum ?: 0
        )
    }
}
