package dsm.pick2024.domain.application.port.`in`

import java.util.UUID

interface UpdateReturnTeacherUseCase {
    fun updateReturnTeacher(userId: UUID)
}
