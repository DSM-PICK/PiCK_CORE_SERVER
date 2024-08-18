package dsm.pick2024.domain.classroom.service

import dsm.pick2024.domain.classroom.port.`in`.BackUserClassroomUseCase
import dsm.pick2024.domain.classroom.port.out.DeleteClassRoomPort
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BackUserClassroomService(
    private val deleteClassRoomPort: DeleteClassRoomPort,
    private val userFacadeUseCase: UserFacadeUseCase
) : BackUserClassroomUseCase {
    @Transactional
    override fun backClassroom() {
        val user = userFacadeUseCase.currentUser()
        deleteClassRoomPort.deleteByUserId(user.xquareId)
    }
}
