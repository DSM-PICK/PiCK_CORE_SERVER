package dsm.pick2024.domain.afterschool.port.`in`

import java.util.UUID

interface DeleteAfterSchoolStudentUseCase {
    fun deleteAfterSchoolStudent(id: UUID)
}
