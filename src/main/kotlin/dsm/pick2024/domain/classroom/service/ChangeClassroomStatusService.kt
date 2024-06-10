package dsm.pick2024.domain.classroom.service

import dsm.pick2024.domain.afterschool.enums.Status
import dsm.pick2024.domain.application.enums.Status.NO
import dsm.pick2024.domain.application.enums.Status.OK
import dsm.pick2024.domain.attendance.domain.Attendance
import dsm.pick2024.domain.attendance.port.out.FindAttendanceByUserIdPort
import dsm.pick2024.domain.attendance.port.out.SaveAllPort
import dsm.pick2024.domain.classroom.domain.Classroom
import dsm.pick2024.domain.classroom.exception.ClassroomNorFoundException
import dsm.pick2024.domain.classroom.port.`in`.ChangeClassroomStatusUseCase
import dsm.pick2024.domain.classroom.port.out.DeleteClassRoomPort
import dsm.pick2024.domain.classroom.port.out.FindByUserIdPort
import dsm.pick2024.domain.classroom.presentation.dto.request.ClassroomStatusRequest
import dsm.pick2024.domain.user.exception.UserNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChangeClassroomStatusService(
    private val findByUserIdPort: FindByUserIdPort,
    private val deleteClassRoomPort: DeleteClassRoomPort,
    private val saveAllClassroomPort: SaveAllClassroomPort,
    private val findAttendanceByUserIdPort: FindAttendanceByUserIdPort,
    private val saveAllPort: SaveAllPort
) : ChangeClassroomStatusUseCase {
    @Transactional
    override fun changeClassroomStatus(request: ClassroomStatusRequest) {
        if (request.status == NO) {
            for (id in request.ids) {
                val classroom = findByUserIdPort.findByUserId(id) ?: throw ClassroomNorFoundException
                deleteClassRoomPort.deleteByUserId(classroom.userId)
            }
            return
        }

        val update = mutableListOf<Classroom>()
        val updateAttendanceList = mutableListOf<Attendance>()

        request.ids.forEach { id ->
            val classroom = findByUserIdPort.findByUserId(id) ?: throw ClassroomNorFoundException

            val updatedClassroom = classroom.copy(status = OK)
            update.add(updatedClassroom)

            val updatedAttendance =
                findAttendanceByUserIdPort.findByUserId(classroom.userId)?.run {
                    copy(
                        period6 = getStatus(classroom, period6, 6),
                        period7 = getStatus(classroom, period7, 7),
                        period8 = getStatus(classroom, period8, 8),
                        period9 = getStatus(classroom, period9, 9),
                        period10 = getStatus(classroom, period10, 10)
                    )
                } ?: throw UserNotFoundException

            updateAttendanceList.add(updatedAttendance)
        }

        saveAllClassroomPort.saveAll(update)
        saveAllPort.saveAll(updateAttendanceList)
    }

    private fun getStatus(
        classroom: Classroom,
        status: Status,
        period: Int
    ) = if (period in classroom.startPeriod..classroom.endPeriod) {
        Status.MOVEMENT
    } else {
        status
    }
}
