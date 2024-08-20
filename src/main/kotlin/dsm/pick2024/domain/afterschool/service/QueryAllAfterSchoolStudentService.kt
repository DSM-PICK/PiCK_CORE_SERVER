package dsm.pick2024.domain.afterschool.service

import dsm.pick2024.domain.afterschool.port.`in`.QueryAllAfterSchoolStudentUseCase
import dsm.pick2024.domain.afterschool.port.out.QueryAfterSchoolStudentPort
import dsm.pick2024.domain.afterschool.presentation.dto.response.QueryAfterSchoolStudentAllResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryAllAfterSchoolStudentService(
    private val queryAfterSchoolPort: QueryAfterSchoolStudentPort
) : QueryAllAfterSchoolStudentUseCase {

    @Transactional(readOnly = true)
    override fun queryAfterSchoolStudentAll() =
        queryAfterSchoolPort.findAll()
            .map { QueryAfterSchoolStudentAllResponse(it) }.sortedWith(
                compareBy({ it.grade }, { it.classNum }, { it.num })
            )
}
