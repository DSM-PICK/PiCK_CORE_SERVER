package dsm.pick2024.domain.classroom.service

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.port.out.ExistsApplicationPort
import dsm.pick2024.domain.classroom.domain.Classroom
import dsm.pick2024.domain.classroom.exception.AleadyApplyingMovementException
import dsm.pick2024.domain.classroom.exception.MoveRequiredOnClubDayException
import dsm.pick2024.domain.classroom.port.`in`.MoveClassroomApplicationUseCase
import dsm.pick2024.domain.classroom.port.out.SaveClassRoomPort
import dsm.pick2024.domain.classroom.port.out.ExistClassRoomPort
import dsm.pick2024.domain.classroom.presentation.dto.request.UserMoveClassroomRequest
import dsm.pick2024.domain.main.port.`in`.MainUseCase
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.DayOfWeek
import java.time.LocalDate

@Service
class MoveClassroomApplicationService(
    private val saveClassRoomPort: SaveClassRoomPort,
    private val existClassRoomPort: ExistClassRoomPort,
    private val userFacadeUseCase: UserFacadeUseCase,
    private val existsApplicationPort: ExistsApplicationPort,
    private val mainUseCase: MainUseCase
) : MoveClassroomApplicationUseCase {

    @Transactional
    override fun moveClassroomApplication(request: UserMoveClassroomRequest) {
        val user = userFacadeUseCase.currentUser()

        val day = LocalDate.now().dayOfWeek

        val moveValue = when (day) {
            DayOfWeek.TUESDAY, DayOfWeek.FRIDAY -> {
                request.move
                    ?: throw MoveRequiredOnClubDayException
            }
            else -> {
                "${user.grade}-${user.classNum}"
            }
        }

        if (existClassRoomPort.existsByUserId(user.id) || existsApplicationPort.existByUserId(user.id)) {
            throw AleadyApplyingMovementException
        }
        saveClassRoomPort.save(
            Classroom(
                userId = user.id,
                userName = user.name,
                move = moveValue,
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

        mainUseCase.sendEvent(user.id)
    }
}
