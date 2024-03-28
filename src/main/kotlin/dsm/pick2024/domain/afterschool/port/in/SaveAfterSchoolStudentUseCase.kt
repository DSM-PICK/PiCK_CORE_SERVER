package dsm.pick2024.domain.afterschool.port.`in`

import dsm.pick2024.domain.afterschool.presentation.dto.request.SaveAfterSchoolStudentRequest

interface SaveAfterSchoolStudentUseCase {
    fun saveAfterSchoolStudent(request: List<SaveAfterSchoolStudentRequest>)
}
