package dsm.pick2024.domain.classroom.service

import dsm.pick2024.domain.classroom.port.`in`.ClassroomFinderUseCase
import dsm.pick2024.domain.classroom.port.`in`.QueryUserMoveClassroomUseCase
import dsm.pick2024.domain.classroom.presentation.dto.response.UserMoveClassroomResponse
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryUserMoveClassroomService(
    private val userFacadeUseCase: UserFacadeUseCase,
    private val classroomFinderUseCase: ClassroomFinderUseCase
) : QueryUserMoveClassroomUseCase {
    @Transactional(readOnly = true)
    override fun queryUserMoveClassroom(): UserMoveClassroomResponse {
        val user = userFacadeUseCase.currentUser()
        val move = classroomFinderUseCase.findOKClassroomOrThrow(user.id)

        return UserMoveClassroomResponse(move.userName, move.classroomName, move.startPeriod, move.endPeriod)
    }
}
