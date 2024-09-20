package dsm.pick2024.domain.main

import dsm.pick2024.domain.application.enums.ApplicationKind
import dsm.pick2024.domain.application.port.out.ExistsApplicationPort
import dsm.pick2024.domain.application.port.out.QueryApplicationPort
import dsm.pick2024.domain.application.presentation.dto.response.QueryMainMyApplicationResponse
import dsm.pick2024.domain.classroom.port.out.ExistClassRoomPort
import dsm.pick2024.domain.classroom.port.out.QueryClassroomPort
import dsm.pick2024.domain.classroom.presentation.dto.response.QueryMainUserMoveClassroomResponse
import dsm.pick2024.domain.earlyreturn.presentation.dto.response.QuerySimpleMyEarlyResponse
import dsm.pick2024.domain.user.exception.UserNotFoundException
import dsm.pick2024.domain.user.port.out.QueryUserPort
import dsm.pick2024.global.config.socket.MainWebSocketHandler
import dsm.pick2024.global.config.socket.WebSocketStatusUpdateEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.socket.WebSocketSession
import java.util.UUID

@Service
class MainService(
    private val queryApplicationPort: QueryApplicationPort,
    private val queryClassroomPort: QueryClassroomPort,
    private val existApplicationPort: ExistsApplicationPort,
    private val existClassRoomPort: ExistClassRoomPort,
    private val queryUserPort: QueryUserPort,
    private val eventPublisher: ApplicationEventPublisher
) {

    @Transactional(readOnly = true)
    fun main(session: WebSocketSession, payload: String): Any? {
        val user = queryUserPort.findByAccountId(payload) ?: throw UserNotFoundException
        val userId = user.xquareId
        val newStatus = findStatus(userId)

        eventPublisher.publishEvent(WebSocketStatusUpdateEvent(this, session, newStatus))

        return newStatus
    }

    private fun findStatus(userId: UUID): Any? {
        return when {
            existApplicationPort.existsOKByUserId(userId, ApplicationKind.APPLICATION) -> findApplication(userId)
            existApplicationPort.existsOKByUserId(userId, ApplicationKind.EARLY_RETURN) -> findEarlyReturn(userId)
            existClassRoomPort.existOKByUserId(userId) -> findClassroom(userId)
            else -> null
        }
    }

    private fun findApplication(userId: UUID) =
        queryApplicationPort.findByUserIdAndStatusAndApplicationKind(userId, ApplicationKind.APPLICATION)?.let {
            QueryMainMyApplicationResponse(
                userId = userId,
                start = it.start.take(5),
                username = it.userName,
                end = it.end!!.take(5),
                type = Main.APPLICATION
            )
        }

    private fun findEarlyReturn(userId: UUID) =
        queryApplicationPort.findByUserIdAndStatusAndApplicationKind(userId, ApplicationKind.EARLY_RETURN)?.let {
            QuerySimpleMyEarlyResponse(
                userId = userId,
                start = it.start.take(5),
                username = it.userName,
                type = Main.EARLYRETURN
            )
        }

    private fun findClassroom(userId: UUID) =
        queryClassroomPort.findByUserId(userId)?.let {
            QueryMainUserMoveClassroomResponse(
                username = it.userName,
                classroom = it.classroomName,
                start = it.startPeriod.toString(),
                end = it.endPeriod.toString(),
                type = Main.CLASSROOM
            )
        }
}
