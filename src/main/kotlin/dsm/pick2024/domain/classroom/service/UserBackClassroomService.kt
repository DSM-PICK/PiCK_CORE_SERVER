package dsm.pick2024.domain.classroom.service

import dsm.pick2024.domain.classroom.port.`in`.UserBackClassroomUseCase
import dsm.pick2024.domain.classroom.port.out.ClassroomDeletePort
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserBackClassroomService(
    private val classroomDeletePort: ClassroomDeletePort,
    private val userFacadeUseCase: UserFacadeUseCase
) : UserBackClassroomUseCase {
    @Transactional
    override fun backClassroom() {
        val user = userFacadeUseCase.currentUser()
        classroomDeletePort.deleteByUserId(user.id!!)
    }
}
