package dsm.pick2024.domain.main

import dsm.pick2024.domain.application.port.out.FindApplicationByUserIdPort
import dsm.pick2024.domain.application.presentation.dto.response.QuerySimpleMyApplicationResponse
import dsm.pick2024.domain.classroom.port.out.FindByUserIdPort
import dsm.pick2024.domain.classroom.presentation.dto.response.UserMoveClassroomResponse
import dsm.pick2024.domain.earlyreturn.port.out.FindEarlyReturnByUserIdPort
import dsm.pick2024.domain.earlyreturn.presentation.dto.response.QuerySimpleMyEarlyResponse
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class MainService(
    private val userFacadeUseCase: UserFacadeUseCase,
    private val findEarlyReturnByUserIdPort: FindEarlyReturnByUserIdPort,
    private val findApplicationByUserIdPort: FindApplicationByUserIdPort,
    private val findByUserIdPort: FindByUserIdPort
) {
    @Transactional(readOnly = true)
    fun main(): Any? {
        val userId = userFacadeUseCase.currentUser().id!!

        return when {
            findApplication(userId) != null -> findApplication(userId)
            findEarlyReturn(userId) != null -> findEarlyReturn(userId)
            else -> findClassroom(userId)
        }
    }

    private fun findApplication(userId: UUID): QuerySimpleMyApplicationResponse? {
        return findApplicationByUserIdPort.findByUserId(userId)?.run {
            QuerySimpleMyApplicationResponse(
                userId = userId,
                startTime = startTime,
                username = username,
                endTime = endTime
            )
        }
    }

    private fun findEarlyReturn(userId: UUID): QuerySimpleMyEarlyResponse? {
        return findEarlyReturnByUserIdPort.findByUserId(userId)?.run {
            QuerySimpleMyEarlyResponse(
                userId = userId,
                startTime = startTime,
                username = username
            )
        }
    }

    private fun findClassroom(userId: UUID): UserMoveClassroomResponse? {
        return findByUserIdPort.findByUserId(userId)?.run {
            UserMoveClassroomResponse(
                username = username,
                classroom = classroomName
            )
        }
    }
}
