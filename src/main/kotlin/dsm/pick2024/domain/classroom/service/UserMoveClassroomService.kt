package dsm.pick2024.domain.classroom.service

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.classroom.domain.Classroom
import dsm.pick2024.domain.classroom.exception.AleadyApplyingMovementException
import dsm.pick2024.domain.classroom.port.`in`.UserMoveClassroomUseCase
import dsm.pick2024.domain.classroom.port.out.SaveClassRoomPort
import dsm.pick2024.domain.classroom.port.out.ExistClassRoomPort
import dsm.pick2024.domain.classroom.presentation.dto.request.UserMoveClassroomRequest
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserMoveClassroomService(
    private val saveClassRoomPort: SaveClassRoomPort,
    private val existClassRoomPort: ExistClassRoomPort,
    private val userFacadeUseCase: UserFacadeUseCase
) : UserMoveClassroomUseCase {

    @Transactional
    override fun moveClassroom(request: UserMoveClassroomRequest) {
        val user = userFacadeUseCase.currentUser()

        if (existClassRoomPort.existsByUserId(user.id)) {
            throw AleadyApplyingMovementException
        }
        saveClassRoomPort.save(
            Classroom(
                userId = user.id,
                userName = user.name,
                classroomName = request.classroomName,
                floor = request.floor,
                grade = user.grade,
                classNum = user.classNum,
                num = user.num,
                startPeriod = request.startPeriod,
                endPeriod = request.endPeriod,
                status = Status.QUIET
            )
        )
    }
}
