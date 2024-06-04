package dsm.pick2024.domain.afterschool.service

import dsm.pick2024.domain.afterschool.port.`in`.QueryAfterSchoolStudentAllUseCase
import dsm.pick2024.domain.afterschool.port.out.QueryAfterSchoolStudentPort
import dsm.pick2024.domain.afterschool.presentation.dto.response.QueryAfterSchoolStudentAllResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryAfterSchoolStudentAllService(
    private val queryAfterSchoolPort: QueryAfterSchoolStudentPort
) : QueryAfterSchoolStudentAllUseCase {

    @Transactional(readOnly = true)
    override fun queryAfterSchoolStudentAll() =
        queryAfterSchoolPort.findAll()
            .map { QueryAfterSchoolStudentAllResponse(it) }
}
