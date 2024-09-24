package dsm.pick2024.domain.main

import dsm.pick2024.domain.application.enums.ApplicationKind
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.port.out.ExistsApplicationPort
import dsm.pick2024.domain.application.port.out.QueryApplicationPort
import dsm.pick2024.domain.application.presentation.dto.response.QueryMainMyApplicationResponse
import dsm.pick2024.domain.classroom.port.out.ExistClassRoomPort
import dsm.pick2024.domain.classroom.port.out.QueryClassroomPort
import dsm.pick2024.domain.classroom.presentation.dto.response.QueryMainUserMoveClassroomResponse
import dsm.pick2024.domain.earlyreturn.presentation.dto.response.QuerySimpleMyEarlyResponse
import dsm.pick2024.domain.main.port.`in`.MainUseCase
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import dsm.pick2024.domain.event.WebSocketStatusUpdateRequest
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.web.socket.WebSocketSession
import java.util.UUID

@Service
class MainService(
    private val queryApplicationPort: QueryApplicationPort,
    private val queryClassroomPort: QueryClassroomPort,
    private val existApplicationPort: ExistsApplicationPort,
    private val existClassRoomPort: ExistClassRoomPort,
    private val eventPublisher: ApplicationEventPublisher,
    private val userFacadeUseCase: UserFacadeUseCase
) : MainUseCase {

    override fun main(userId: String, session: WebSocketSession) {
        val user = userFacadeUseCase.getUserByAccountId(userId)
        val newStatus = findStatus(user.xquareId)
        publishStatusUpdate(newStatus, user.accountId)
    }

    override fun onHandleEvent(userId: UUID) {
        val user = userFacadeUseCase.getUserByXquareId(userId)
        val newStatus = findStatus(user.xquareId)
        publishStatusUpdate(newStatus, user.accountId)
    }

    private fun findStatus(userId: UUID): Any? {
        return when {
            applicationExist(userId, ApplicationKind.APPLICATION) -> findApplication(userId)
            applicationExist(userId, ApplicationKind.EARLY_RETURN) -> findEarlyReturn(userId)
            classRoomExists(userId) -> findClassroom(userId)
            else -> findWaitingStatus(userId)
        }
    }

    private fun applicationExist(userId: UUID, kind: ApplicationKind) =
        existApplicationPort.existsByStatusAndUserIdAndApplicationKind(Status.OK, userId, kind)

    private fun classRoomExists(userId: UUID) = existClassRoomPort.existOKByUserId(userId)

    private fun findWaitingStatus(userId: UUID): WaitingResponse? {
        return when {
            existsQuietApplication(userId, ApplicationKind.APPLICATION) -> waiting(userId, Main.APPLICATION)
            existsQuietApplication(userId, ApplicationKind.EARLY_RETURN) -> waiting(userId, Main.EARLY_RETURN)
            existClassRoomPort.existsByUserId(userId) -> waiting(userId, Main.CLASSROOM)
            else -> null
        }
    }

    private fun existsQuietApplication(userId: UUID, kind: ApplicationKind) =
        existApplicationPort.existsByUserIdAndApplicationKind(userId, kind)

    private fun findApplication(userId: UUID) =
        queryApplicationPort.findByUserIdAndStatusAndApplicationKind(
            Status.OK,
            userId,
            ApplicationKind.APPLICATION
        )?.let {
            QueryMainMyApplicationResponse(
                userId = userId,
                start = it.start.take(5),
                userName = it.userName,
                end = it.end!!.take(5),
                type = Main.APPLICATION
            )
        }

    private fun findEarlyReturn(userId: UUID) =
        queryApplicationPort.findByUserIdAndStatusAndApplicationKind(
            Status.OK,
            userId,
            ApplicationKind.EARLY_RETURN
        )?.let {
            QuerySimpleMyEarlyResponse(
                userId = userId,
                start = it.start.take(5),
                userName = it.userName,
                type = Main.EARLY_RETURN
            )
        }

    private fun findClassroom(userId: UUID) =
        queryClassroomPort.findByUserId(userId)?.let {
            QueryMainUserMoveClassroomResponse(
                userName = it.userName,
                classroom = it.classroomName,
                start = it.startPeriod.toString(),
                end = it.endPeriod.toString(),
                type = Main.CLASSROOM
            )
        }

    private fun waiting(userId: UUID, type: Main): WaitingResponse? {
        val status = when (type) {
            Main.APPLICATION -> queryApplicationPort.findByUserIdAndStatusAndApplicationKind(
                Status.QUIET,
                userId,
                ApplicationKind.APPLICATION
            )?.status
            Main.EARLY_RETURN -> queryApplicationPort.findByUserIdAndStatusAndApplicationKind(
                Status.QUIET,
                userId,
                ApplicationKind.EARLY_RETURN
            )?.status
            Main.CLASSROOM -> queryClassroomPort.findByUserId(userId)?.status
        }

        return if (status == Status.QUIET) WaitingResponse(type) else null
    }

    private fun publishStatusUpdate(newStatus: Any?, accountId: String) {
        eventPublisher.publishEvent(WebSocketStatusUpdateRequest(this, newStatus, accountId))
    }
}
