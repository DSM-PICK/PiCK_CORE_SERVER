package dsm.pick2024.domain.afterschool.port.`in`

import dsm.pick2024.domain.afterschool.presentation.dto.request.ChangeAfterSchoolStatusRequest

interface ChangeStatusAfterSchoolStudentUseCase {
    fun changeStatusAfterSchoolStudent(request: ChangeAfterSchoolStatusRequest)
}
