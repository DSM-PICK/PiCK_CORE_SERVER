package dsm.pick2024.domain.classroom.service

import dsm.pick2024.domain.attendance.enums.AttendanceStatus
import dsm.pick2024.domain.application.enums.Status.NO
import dsm.pick2024.domain.application.enums.Status.OK
import dsm.pick2024.domain.attendance.domain.Attendance
import dsm.pick2024.domain.attendance.port.out.QueryAttendancePort
import dsm.pick2024.domain.attendance.port.out.SaveAttendancePort
import dsm.pick2024.domain.classroom.domain.Classroom
import dsm.pick2024.domain.classroom.exception.ClassroomNotFoundException
import dsm.pick2024.domain.classroom.port.`in`.ChangeClassroomStatusUseCase
import dsm.pick2024.domain.classroom.port.out.DeleteClassRoomPort
import dsm.pick2024.domain.classroom.port.out.QueryClassroomPort
import dsm.pick2024.domain.classroom.port.out.SaveClassRoomPort
import dsm.pick2024.domain.classroom.presentation.dto.request.ClassroomStatusRequest
import dsm.pick2024.domain.event.classroom.SendMessageToClassroomEventPot
import dsm.pick2024.domain.user.exception.UserNotFoundException
import dsm.pick2024.domain.user.port.out.QueryUserPort
import java.util.UUID
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChangeClassroomStatusService(
    private val queryClassroomPort: QueryClassroomPort,
    private val deleteClassRoomPort: DeleteClassRoomPort,
    private val saveClassRoomPort: SaveClassRoomPort,
    private val queryAttendancePort: QueryAttendancePort,
    private val saveAttendancePort: SaveAttendancePort,
    private val queryUserPort: QueryUserPort,
    private val sendMessageToClassroomEventPot: SendMessageToClassroomEventPot
) : ChangeClassroomStatusUseCase {
    @Transactional
    override fun changeClassroomStatus(request: ClassroomStatusRequest) {
        if (request.status == NO) {
            handleNo(request.ids)
        } else {
            handleOk(request.ids)
        }
    }

    private fun handleNo(ids: List<UUID>) {
        ids.forEach { id ->
            val classroom = queryClassroomPort.findByUserId(id) ?: throw ClassroomNotFoundException
            val user = queryUserPort.findByXquareId(classroom!!.userId)!!
            sendMessageToClassroomEventPot.send(user.deviceToken!!, NO, null)
            deleteClassRoomPort.deleteByUserId(id)
        }
    }

    private fun handleOk(ids: List<UUID>) {
        val updateClassroom = mutableListOf<Classroom>()
        val updateAttendanceList = mutableListOf<Attendance>()

        ids.forEach { id ->
            val classroom = queryClassroomPort.findByUserId(id) ?: throw ClassroomNotFoundException
            val user = queryUserPort.findByXquareId(classroom!!.userId)!!
            val updatedClassroom = classroom.copy(status = OK)

            updateClassroom.add(updatedClassroom)

            val updatedAttendance =
                queryAttendancePort.findByUserId(user.xquareId)?.run {
                    copy(
                        period6 = getStatus(classroom, period6, 6),
                        period7 = getStatus(classroom, period7, 7),
                        period8 = getStatus(classroom, period8, 8),
                        period9 = getStatus(classroom, period9, 9),
                        period10 = getStatus(classroom, period10, 10)
                    )
                } ?: throw UserNotFoundException

            updateAttendanceList.add(updatedAttendance)
            sendMessageToClassroomEventPot.send(user.deviceToken!!, OK, classroom)
        }
        saveClassRoomPort.saveAll(updateClassroom)
        saveAttendancePort.saveAll(updateAttendanceList)
    }

    private fun getStatus(
        classroom: Classroom,
        status: AttendanceStatus,
        period: Int
    ) = if (period in classroom.startPeriod..classroom.endPeriod) {
        AttendanceStatus.MOVEMENT
    } else {
        status
    }
}
