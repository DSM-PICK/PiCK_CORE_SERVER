package dsm.pick2024.domain.classroom.service

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.port.out.ExistsApplicationPort
import dsm.pick2024.domain.classroom.domain.Classroom
import dsm.pick2024.domain.classroom.exception.AleadyApplyingMovementException
import dsm.pick2024.domain.classroom.exception.UnableApplyException
import dsm.pick2024.domain.classroom.port.`in`.MoveClassroomApplicationUseCase
import dsm.pick2024.domain.classroom.port.out.SaveClassRoomPort
import dsm.pick2024.domain.classroom.port.out.ExistClassRoomPort
import dsm.pick2024.domain.classroom.presentation.dto.request.UserMoveClassroomRequest
import dsm.pick2024.domain.event.dto.UserInfoRequest
import dsm.pick2024.domain.event.enums.EventTopic
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MoveClassroomApplicationService(
    private val saveClassRoomPort: SaveClassRoomPort,
    private val existClassRoomPort: ExistClassRoomPort,
    private val userFacadeUseCase: UserFacadeUseCase,
    private val existsApplicationPort: ExistsApplicationPort,
    private val eventPublisher: ApplicationEventPublisher
) : MoveClassroomApplicationUseCase {

    @Transactional
    override fun moveClassroomApplication(request: UserMoveClassroomRequest) {
        val user = userFacadeUseCase.currentUser()

        if (existsApplicationPort.existByUserId(user.id)) {
            throw UnableApplyException
        }

        if (existClassRoomPort.existsByUserId(user.id)) {
            throw AleadyApplyingMovementException
        }
        saveClassRoomPort.save(
            Classroom(
                userId = user.id,
                userName = user.name,
                classroomName = request.classroomName,
                floor = request.floor,
                grade = user.grade,
                classNum = user.classNum,
                num = user.num,
                startPeriod = request.start,
                endPeriod = request.end,
                status = Status.QUIET
            )
        )
        eventPublisher.publishEvent(UserInfoRequest(EventTopic.HANDLE_EVENT, user.id))
    }
}
