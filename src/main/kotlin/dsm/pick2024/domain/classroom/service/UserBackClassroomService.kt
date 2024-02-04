package dsm.pick2024.domain.classroom.service

import dsm.pick2024.domain.classroom.port.`in`.UserBackClassroomUseCase
import dsm.pick2024.domain.classroom.port.out.ClassroomDeletePort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class UserBackClassroomService(
    private val classroomDeletePort: ClassroomDeletePort,
): UserBackClassroomUseCase {

    @Transactional
    override fun backClassroom(id: UUID) {
        classroomDeletePort.deleteById(id)
    }
}
