package dsm.pick2024.domain.classroom.port.`in`

import dsm.pick2024.domain.classroom.presentation.dto.request.ClassroomStatusRequest

interface ChangeClassroomStatusUseCase {
    fun changeClassroomStatus(request: ClassroomStatusRequest)
}
