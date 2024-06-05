package dsm.pick2024.domain.main

import dsm.pick2024.domain.application.port.out.ExistsApplicationPort
import dsm.pick2024.domain.application.port.out.QueryApplicationPort
import dsm.pick2024.domain.application.presentation.dto.response.QueryMainMyApplicationResponse
import dsm.pick2024.domain.classroom.port.out.ExistOKByUserIdPort
import dsm.pick2024.domain.classroom.port.out.FindByUserIdPort
import dsm.pick2024.domain.classroom.presentation.dto.response.QueryMainUserMoveClassroomResponse
import dsm.pick2024.domain.earlyreturn.port.out.ExistsOKEarlyReturnByUserIDPort
import dsm.pick2024.domain.earlyreturn.port.out.QueryOKMyEarlyReturn
import dsm.pick2024.domain.earlyreturn.presentation.dto.response.QuerySimpleMyEarlyResponse
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.format.DateTimeFormatter
import java.util.UUID

@Service
class MainService(
    private val userFacadeUseCase: UserFacadeUseCase,
    private val queryApplicationPort: QueryApplicationPort,
    private val findByUserIdPort: FindByUserIdPort,
    private val existApplicationPort: ExistsApplicationPort,
    private val existsOKEarlyReturnByUserIDPort: ExistsOKEarlyReturnByUserIDPort,
    private val existOKByUserIdPort: ExistOKByUserIdPort,
    private val queryOKMyEarlyReturn: QueryOKMyEarlyReturn
) {
    @Transactional(readOnly = true)
    fun main(): Any? {
        val userId = userFacadeUseCase.currentUser().id

        return when {
            existApplicationPort.existsOKByUserId(userId) -> findApplication(userId)
            existsOKEarlyReturnByUserIDPort.existsOKByUserId(userId) -> findEarlyReturn(userId)
            existOKByUserIdPort.existOKByUserId(userId) -> findClassroom(userId)
            else -> null
        }
    }

    private fun findApplication(userId: UUID): QueryMainMyApplicationResponse {
        return queryApplicationPort.findOKApplication(userId)?.run {
            QueryMainMyApplicationResponse(
                userId = userId,
                startTime = startTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                username = userName,
                endTime = endTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                type = Main.APPLICATION
            )
        }!!
    }

    private fun findEarlyReturn(userId: UUID): QuerySimpleMyEarlyResponse {
        return queryOKMyEarlyReturn.findByOKEarlyReturn(userId)?.run {
            QuerySimpleMyEarlyResponse(
                userId = userId,
                startTime = startTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                username = userName,
                type = Main.EARLYRETURN
            )
        }!!
    }

    private fun findClassroom(userId: UUID): QueryMainUserMoveClassroomResponse {
        return findByUserIdPort.findByUserId(userId)?.run {
            QueryMainUserMoveClassroomResponse(
                username = userName,
                classroom = classroomName,
                startPeriod = startPeriod,
                endPeriod = endPeriod,
                type = Main.CLASSROOM
            )
        }!!
    }
}
