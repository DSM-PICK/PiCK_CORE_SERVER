package dsm.pick2024.domain.afterschool.port.out

import java.util.UUID

interface DeleteAfterSchoolStudentPort {
    fun deleteById(id: UUID)
}
