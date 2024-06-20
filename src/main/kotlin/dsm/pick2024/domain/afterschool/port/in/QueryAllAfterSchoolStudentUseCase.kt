package dsm.pick2024.domain.afterschool.port.`in`

import dsm.pick2024.domain.afterschool.presentation.dto.response.QueryAfterSchoolStudentAllResponse

interface QueryAllAfterSchoolStudentUseCase {
    fun queryAfterSchoolStudentAll(): List<QueryAfterSchoolStudentAllResponse>
}
