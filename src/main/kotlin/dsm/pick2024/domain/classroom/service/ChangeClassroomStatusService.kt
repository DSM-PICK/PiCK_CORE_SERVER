package dsm.pick2024.domain.classroom.service

import dsm.pick2024.domain.afterschool.enums.Status.*
import dsm.pick2024.domain.application.enums.Status
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
        val update = mutableListOf<Classroom>()
        val updateAttendanceList = mutableListOf<Attendance>()
        if (request.status == Status.NO) {
            for (id in request.ids) {
                val classroom = findByUserIdPort.findByUserId(id) ?: throw ClassroomNorFoundException
                classroomDeletePort.deleteByUserId(classroom.userId!!)
            }
            return
        }

        for (id in request.ids) {
            val classroom = findByUserIdPort.findByUserId(id) ?: throw ClassroomNorFoundException

            val updatedClassroom = classroom.copy(status = Status.OK)
            update.add(updatedClassroom)

            val attendance =
                findAttendanceByUserIdPort.findByUserId(classroom.userId)
                    ?: throw UserNotFoundException

            val period = classroom.startPeriod..classroom.endPeriod
            val updatedAttendance =
                attendance.copy(
                    period6 = if (6 in period) MOVEMENT else attendance.period6,
                    period7 = if (7 in period) MOVEMENT else attendance.period7,
                    period8 = if (8 in period) MOVEMENT else attendance.period8,
                    period9 = if (9 in period) MOVEMENT else attendance.period9,
                    period10 = if (10 in period) MOVEMENT else attendance.period10
                )
            updateAttendanceList.add(updatedAttendance)
        }
        saveAllClassroomPort.saveAll(update)
        saveAll.saveAll(updateAttendanceList)
    }
}
