package dsm.pick2024.domain.classroom.service

import dsm.pick2024.domain.afterschool.enums.Status
import dsm.pick2024.domain.application.enums.Status.NO
import dsm.pick2024.domain.application.enums.Status.OK
import dsm.pick2024.domain.attendance.domain.Attendance
import dsm.pick2024.domain.attendance.port.out.FindAttendanceByUserIdPort
import dsm.pick2024.domain.attendance.port.out.SaveAll
import dsm.pick2024.domain.classroom.domain.Classroom
import dsm.pick2024.domain.classroom.exception.ClassroomNorFoundException
import dsm.pick2024.domain.classroom.port.`in`.ChangeClassroomStatusUseCase
import dsm.pick2024.domain.classroom.port.out.ClassroomDeletePort
import dsm.pick2024.domain.classroom.port.out.FindByUserIdPort
import dsm.pick2024.domain.classroom.port.out.SaveAllClassroomPort
import dsm.pick2024.domain.classroom.presentation.dto.request.ClassroomStatusRequest
import dsm.pick2024.domain.user.exception.UserNotFoundException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class ChangeClassroomStatusService(
    private val findByUserIdPort: FindByUserIdPort,
    private val classroomDeletePort: ClassroomDeletePort,
    private val saveAllClassroomPort: SaveAllClassroomPort,
    private val findAttendanceByUserIdPort: FindAttendanceByUserIdPort,
    private val saveAll: SaveAll
) : ChangeClassroomStatusUseCase {
    @Transactional
    override fun changeClassroomStatus(request: ClassroomStatusRequest) {
        if (request.status == NO) {
            for (id in request.ids) {
                val classroom = findByUserIdPort.findByUserId(id) ?: throw ClassroomNorFoundException
                classroomDeletePort.deleteByUserId(classroom.userId!!)
            }
            return
        }

        val update = mutableListOf<Classroom>()
        val updateAttendanceList = mutableListOf<Attendance>()

        request.ids.forEach { id ->
            val classroom = findByUserIdPort.findByUserId(id) ?: throw ClassroomNorFoundException

            val updatedClassroom = classroom.copy(status = OK)
            update.add(updatedClassroom)

            val attendance =
                findAttendanceByUserIdPort.findByUserId(classroom.userId)
                    ?: throw UserNotFoundException

            val updatedAttendance =
                attendance.copy(
                    period6 = getStatus(classroom, attendance.period6, 6),
                    period7 = getStatus(classroom, attendance.period7, 7),
                    period8 = getStatus(classroom, attendance.period8, 8),
                    period9 = getStatus(classroom, attendance.period9, 9),
                    period10 = getStatus(classroom, attendance.period10, 10)
                )
            updateAttendanceList.add(updatedAttendance)
        }

        saveAllClassroomPort.saveAll(update)
        saveAll.saveAll(updateAttendanceList)
    }

    private fun getStatus(
        classroom: Classroom,
        status: Status,
        period: Int
    ) = if (period in classroom.startPeriod..classroom.endPeriod) Status.MOVEMENT else status
}
