package dsm.pick2024.domain.main

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.port.out.ExistsApplicationPort
import dsm.pick2024.domain.application.port.out.QueryApplicationPort
import dsm.pick2024.domain.application.presentation.dto.response.QueryMainMyApplicationResponse
import dsm.pick2024.domain.classroom.port.out.ExistClassRoomPort
import dsm.pick2024.domain.classroom.port.out.QueryClassroomPort
import dsm.pick2024.domain.classroom.presentation.dto.response.QueryMainUserMoveClassroomResponse
import dsm.pick2024.domain.earlyreturn.port.out.ExistsEarlyReturnPort
import dsm.pick2024.domain.earlyreturn.port.out.QueryEarlyReturnPort
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
    private val queryClassroomPort: QueryClassroomPort,
    private val existApplicationPort: ExistsApplicationPort,
    private val existsEarlyReturnPort: ExistsEarlyReturnPort,
    private val existClassRoomPort: ExistClassRoomPort,
    private val queryEarlyReturnPort: QueryEarlyReturnPort
) {
    @Transactional(readOnly = true)
    fun main(): Any? {
        val userId = userFacadeUseCase.currentUser().id

        return when {
            existApplicationPort.existsOKByUserId(userId) -> findApplication(userId)
            existsEarlyReturnPort.existsOKByUserId(userId) -> findEarlyReturn(userId)
            existClassRoomPort.existOKByUserId(userId) -> findClassroom(userId)
            existApplicationPort.existsByUserId(userId) -> waiting(userId, Main.APPLICATION)
            existsEarlyReturnPort.existsByUserId(userId) -> waiting(userId, Main.EARLYRETURN)
            existClassRoomPort.existsByUserId(userId) -> waiting(userId, Main.CLASSROOM)
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
        return queryEarlyReturnPort.findByOKEarlyReturn(userId)?.run {
            QuerySimpleMyEarlyResponse(
                userId = userId,
                startTime = startTime.format(DateTimeFormatter.ofPattern("HH:mm")),
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
                startPeriod = startPeriod,
                endPeriod = endPeriod,
                type = Main.CLASSROOM
            )
        }!!
    }

    private fun waiting(userId: UUID, type: Main): Any {
        when (type) {
            Main.APPLICATION -> queryApplicationPort.findByUserId(userId).takeIf { it!!.status == Status.QUIET }
            Main.EARLYRETURN -> queryEarlyReturnPort.findByUserId(userId).takeIf { it!!.status == Status.QUIET }
            Main.CLASSROOM -> queryClassroomPort.findByUserId(userId).takeIf { it!!.status == Status.QUIET }
        }
        return WaitingResponse(
            type
        )
    }
}
