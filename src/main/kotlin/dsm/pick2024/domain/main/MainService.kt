package dsm.pick2024.domain.main

import dsm.pick2024.domain.application.enums.ApplicationKind
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.event.ApplicationStatusChangeEvent
import dsm.pick2024.domain.application.port.out.ExistsApplicationPort
import dsm.pick2024.domain.application.port.out.QueryApplicationPort
import dsm.pick2024.domain.application.presentation.dto.response.QueryMainMyApplicationResponse
import dsm.pick2024.domain.classroom.port.out.ExistClassRoomPort
import dsm.pick2024.domain.classroom.port.out.QueryClassroomPort
import dsm.pick2024.domain.classroom.presentation.dto.response.QueryMainUserMoveClassroomResponse
import dsm.pick2024.domain.earlyreturn.presentation.dto.response.QuerySimpleMyEarlyResponse
import dsm.pick2024.domain.user.domain.User
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import dsm.pick2024.global.config.socket.WebSocketStatusUpdateEvent
import dsm.pick2024.global.security.jwt.JwtTokenProvider
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.ApplicationListener
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
    private val userFacadeUseCase: UserFacadeUseCase,
    private val jwtTokenProvider: JwtTokenProvider,
): ApplicationListener<ApplicationStatusChangeEvent> {

    private var event: ApplicationStatusChangeEvent? = null
    fun main(session: WebSocketSession, authorization: String): Any? {
        val token = authorization.removePrefix("Bearer ")
        val claims = jwtTokenProvider.getClaimsToken(token)
        val userId = claims.get("sub", String::class.java)
        val user = userFacadeUseCase.getUserByAccountId(userId)
        return if (event != null) {
            val newStatus = findStatus(user.xquareId)
            eventPublisher.publishEvent(WebSocketStatusUpdateEvent(this, session, newStatus))
        } else {
            null
        }
    }

    override fun onApplicationEvent(event: ApplicationStatusChangeEvent) {
        this.event = event
    }

    private fun handleApplicationStatusChange(event: ApplicationStatusChangeEvent, user: User): Boolean {
        return event.userIdList.contains(user.xquareId)
    }

    private fun findStatus(userId: UUID): Any? {
        return when {
            existApplicationPort.existsByStatusAndUserIdAndApplicationKind(
                Status.OK,
                userId,
                ApplicationKind.APPLICATION
            ) -> findApplication(userId)
            existApplicationPort.existsByStatusAndUserIdAndApplicationKind(
                Status.OK,
                userId,
                ApplicationKind.EARLY_RETURN
            ) -> findEarlyReturn(userId)
            existClassRoomPort.existOKByUserId(userId) -> findClassroom(userId)
            existApplicationPort.existsByUserIdAndApplicationKind(
                userId,
                ApplicationKind.APPLICATION
            ) -> waiting(userId, Main.APPLICATION)
            existApplicationPort.existsByUserIdAndApplicationKind(
                userId,
                ApplicationKind.EARLY_RETURN
            ) -> waiting(userId, Main.EARLYRETURN)
            existClassRoomPort.existsByUserId(userId) -> waiting(userId, Main.CLASSROOM)

            else -> null
        }
    }

    private fun findApplication(userId: UUID) =
        queryApplicationPort
            .findByUserIdAndStatusAndApplicationKind(Status.OK, userId, ApplicationKind.APPLICATION)?.let {
                QueryMainMyApplicationResponse(
                    userId = userId,
                    start = it.start.take(5),
                    username = it.userName,
                    end = it.end!!.take(5),
                    type = Main.APPLICATION
                )
            }

    private fun findEarlyReturn(userId: UUID) =
        queryApplicationPort
            .findByUserIdAndStatusAndApplicationKind(Status.OK, userId, ApplicationKind.EARLY_RETURN)?.let {
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

    private fun waiting(userId: UUID, type: Main): WaitingResponse? {
        val status = when (type) {
            Main.APPLICATION -> queryApplicationPort.findByUserIdAndStatusAndApplicationKind(
                Status.QUIET,
                userId,
                ApplicationKind.APPLICATION
            )?.status

            Main.EARLYRETURN -> queryApplicationPort.findByUserIdAndStatusAndApplicationKind(
                Status.QUIET,
                userId,
                ApplicationKind.EARLY_RETURN
            )?.status

            Main.CLASSROOM -> queryClassroomPort.findByUserId(userId)?.status
        }

        return if (status == Status.QUIET) {
            WaitingResponse(type)
        } else {
            null
        }
    }
}
