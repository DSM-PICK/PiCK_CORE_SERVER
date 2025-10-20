package dsm.pick2024.domain.status.service

import dsm.pick2024.domain.admin.exception.AdminNotFoundException
import dsm.pick2024.domain.admin.persistence.AdminPersistenceAdapter
import dsm.pick2024.domain.status.port.`in`.QueryClassStatusUseCase
import dsm.pick2024.domain.status.port.out.QueryStatusPort
import dsm.pick2024.domain.status.presentation.dto.response.QueryClassResponse
import dsm.pick2024.domain.status.presentation.dto.response.QueryClassStatusResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryClassStatusService(
    private val queryStatusPort: QueryStatusPort,
    private val adminPersistenceAdapter: AdminPersistenceAdapter
) : QueryClassStatusUseCase {
    @Transactional(readOnly = true)
    override fun queryClasStatus(
        grade: Int,
        classNum: Int
    ): QueryClassResponse {
        val teacher = adminPersistenceAdapter.findByGradeAndClassNum(grade, classNum) ?: throw AdminNotFoundException

        val classStatusList =
            queryStatusPort.findByGradeAndClassNum(grade, classNum)
                .map { classStatus ->

                    QueryClassStatusResponse(
                        userId = classStatus.userId,
                        name = classStatus.userName,
                        grade = classStatus.grade,
                        classNum = classStatus.classNum,
                        num = classStatus.num,
                        status = classStatus.status
                    )
                }.distinctBy { it.userId }.sortedWith(
                    compareBy { it.num }
                )

        return QueryClassResponse(
            teacher = teacher!!.name,
            students = classStatusList
        )
    }
}
