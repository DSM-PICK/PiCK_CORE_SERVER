package dsm.pick2024.domain.main

import dsm.pick2024.domain.application.enums.ApplicationKind
import dsm.pick2024.domain.application.port.out.ExistsApplicationPort
import dsm.pick2024.domain.application.port.out.QueryApplicationPort
import dsm.pick2024.domain.application.presentation.dto.response.QueryMainMyApplicationResponse
import dsm.pick2024.domain.classroom.port.out.ExistClassRoomPort
import dsm.pick2024.domain.classroom.port.out.QueryClassroomPort
import dsm.pick2024.domain.classroom.presentation.dto.response.QueryMainUserMoveClassroomResponse
import dsm.pick2024.domain.earlyreturn.presentation.dto.response.QuerySimpleMyEarlyResponse
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class MainService(
    private val userFacadeUseCase: UserFacadeUseCase,
    private val queryApplicationPort: QueryApplicationPort,
    private val queryClassroomPort: QueryClassroomPort,
    private val existApplicationPort: ExistsApplicationPort,
    private val existClassRoomPort: ExistClassRoomPort
) {
    @Transactional(readOnly = true)
    fun main(): Any? {
        val userId = userFacadeUseCase.currentUser().xquareId

        return when {
            existApplicationPort.existsOKByUserId(userId, ApplicationKind.APPLICATION) -> findApplication(userId)
            existApplicationPort.existsOKByUserId(userId, ApplicationKind.EARLY_RETURN) -> findEarlyReturn(userId)
            existClassRoomPort.existOKByUserId(userId) -> findClassroom(userId)
            /*existApplicationPort.existsByUserId(userId) -> waiting(userId, Main.APPLICATION)
            existsEarlyReturnPort.existsByUserId(userId) -> waiting(userId, Main.EARLYRETURN)
            existClassRoomPort.existsByUserId(userId) -> waiting(userId, Main.CLASSROOM)*/
            else -> null
        }
    }

    private fun findApplication(userId: UUID): QueryMainMyApplicationResponse {
        return queryApplicationPort.findByUserIdAndStatusAndApplicationKind(userId, ApplicationKind.APPLICATION)?.run {
            QueryMainMyApplicationResponse(
                userId = userId,
                start = start.take(5),
                username = userName,
                end = end!!.take(5),
                type = Main.APPLICATION
            )
        }!!
    }

    private fun findEarlyReturn(userId: UUID): QuerySimpleMyEarlyResponse {
        return queryApplicationPort.findByUserIdAndStatusAndApplicationKind(userId, ApplicationKind.EARLY_RETURN)?.run {
            QuerySimpleMyEarlyResponse(
                userId = userId,
                start = start.take(5),
                username = userName,
                type = Main.EARLYRETURN
            )
        }!!
    }

    private fun findClassroom(userId: UUID): QueryMainUserMoveClassroomResponse {
        return queryClassroomPort.findByUserId(userId)?.run {
            QueryMainUserMoveClassroomResponse(
                username = userName,
                classroom = classroomName,
                start = startPeriod,
                end = endPeriod,
                type = Main.CLASSROOM
            )
        }!!
    }

    /*private fun waiting(userId: UUID, type: Main): WaitingResponse? {
        val status = when (type) {
            Main.APPLICATION -> queryApplicationPort.findByUserId(userId)?.status
            Main.EARLYRETURN -> queryEarlyReturnPort.findByUserId(userId)?.status
            Main.CLASSROOM -> queryClassroomPort.findByUserId(userId)?.status
        }

        return if (status == Status.QUIET) {
            WaitingResponse(type)
        } else {
            null
        }
    }*/
}
