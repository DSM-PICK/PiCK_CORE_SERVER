package dsm.pick2024.domain.selfstudy.service

import dsm.pick2024.domain.admin.port.`in`.AdminFacadeUseCase
import dsm.pick2024.domain.selfstudy.port.`in`.QueryMySelfStudyUseCase
import dsm.pick2024.domain.selfstudy.port.out.QuerySelfStudyPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryMySelfStudyService(
    private val adminFacadeUseCase: AdminFacadeUseCase,
    private val querySelfStudyPort: QuerySelfStudyPort
) : QueryMySelfStudyUseCase {

    @Transactional(readOnly = true)
    override fun queryMySelfStudy(): String {
        val admin = adminFacadeUseCase.currentAdmin()

        val selfStudy = querySelfStudyPort.findByTodayTeacher(admin.name)
        val floor = selfStudy?.floor

        return if (floor != null) {
            "${admin.name} 선생님은 ${floor}층 자습감독 입니다"
        } else {
            "오늘 ${admin.name} 선생님은 자습감독이 아닙니다."
        }
    }
}
