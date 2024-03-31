package dsm.pick2024.domain.classroom.service

import dsm.pick2024.domain.classroom.port.`in`.QueryUserMoveClassroomUseCase
import dsm.pick2024.domain.classroom.port.out.FindByUserIdPort
import dsm.pick2024.domain.classroom.presentation.dto.response.UserMoveClassroomResponse
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserMoveClassroomQueryService(
    private val userFacadeUseCase: UserFacadeUseCase,
    private val findByUserIdPort: FindByUserIdPort
) : QueryUserMoveClassroomUseCase {
    @Transactional(readOnly = true)
    override fun queryUserMoveClassroom(): UserMoveClassroomResponse {
        val user = userFacadeUseCase.currentUser()
        val move = findByUserIdPort.findByUserId(user.id!!)!!
        return UserMoveClassroomResponse(move.username, move.classroomName)
    }
}
