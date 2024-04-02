package dsm.pick2024.domain.main

import dsm.pick2024.domain.application.port.out.ExistsOKApplicationByUserIdPort
import dsm.pick2024.domain.application.port.out.QueryOKMyApplication
import dsm.pick2024.domain.application.presentation.dto.response.QueryMainMyApplicationResponse
import dsm.pick2024.domain.classroom.port.out.ExistsByUserIdPort
import dsm.pick2024.domain.classroom.port.out.FindByUserIdPort
import dsm.pick2024.domain.classroom.presentation.dto.response.QueryMainUserMoveClassroomResponse
import dsm.pick2024.domain.earlyreturn.port.out.ExistsOKEarlyReturnByUserIDPort
import dsm.pick2024.domain.earlyreturn.port.out.QueryOKMyEarlyReturn
import dsm.pick2024.domain.earlyreturn.presentation.dto.response.QuerySimpleMyEarlyResponse
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.temporal.ChronoUnit
import java.util.UUID

@Service
class MainService(
    private val userFacadeUseCase: UserFacadeUseCase,
    private val queryOKMyApplication: QueryOKMyApplication,
    private val findByUserIdPort: FindByUserIdPort,
    private val existsOKApplicationByUserIdPort: ExistsOKApplicationByUserIdPort,
    private val existsOKEarlyReturnByUserIDPort: ExistsOKEarlyReturnByUserIDPort,
    private val existsByUserIdPort: ExistsByUserIdPort,
    private val queryOKMyEarlyReturn: QueryOKMyEarlyReturn
) {
    @Transactional(readOnly = true)
    fun main(): Any? {
        val userId = userFacadeUseCase.currentUser().id

        return when {
            existsOKApplicationByUserIdPort.existsOKByUserId(userId) -> findApplication(userId)
            existsOKEarlyReturnByUserIDPort.existsOKByUserId(userId) -> findEarlyReturn(userId)
            existsByUserIdPort.existsByUserId(userId) -> findClassroom(userId)
            else -> null
        }
    }

    private fun findApplication(userId: UUID): QueryMainMyApplicationResponse {
        return queryOKMyApplication.findOKApplication(userId)?.run {
            QueryMainMyApplicationResponse(
                userId = userId,
                startTime = startTime.truncatedTo(ChronoUnit.MINUTES),
                username = username,
                endTime = endTime.truncatedTo(ChronoUnit.MINUTES),
                type = Main.APPLICATION
            )
        }!!
    }

    private fun findEarlyReturn(userId: UUID): QuerySimpleMyEarlyResponse {
        return queryOKMyEarlyReturn.findByOKEarlyReturn(userId)?.run {
            QuerySimpleMyEarlyResponse(
                userId = userId,
                startTime = startTime.truncatedTo(ChronoUnit.MINUTES),
                username = username,
                type = Main.EARLYRETURN
            )
        }!!
    }

    private fun findClassroom(userId: UUID): QueryMainUserMoveClassroomResponse {
        return findByUserIdPort.findByUserId(userId)?.run {
            QueryMainUserMoveClassroomResponse(
                username = username,
                classroom = classroomName,
                type = Main.CLASSROOM
            )
        }!!
    }
}
