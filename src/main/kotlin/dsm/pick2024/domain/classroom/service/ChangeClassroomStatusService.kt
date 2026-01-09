package dsm.pick2024.domain.classroom.service

import dsm.pick2024.domain.attendance.enums.AttendanceStatus
import dsm.pick2024.domain.application.enums.Status.NO
import dsm.pick2024.domain.application.enums.Status.OK
import dsm.pick2024.domain.attendance.domain.Attendance
import dsm.pick2024.domain.attendance.port.`in`.AttendanceFinderUseCase
import dsm.pick2024.domain.attendance.port.out.SaveAttendancePort
import dsm.pick2024.domain.classroom.domain.Classroom
import dsm.pick2024.domain.classroom.port.`in`.ChangeClassroomStatusUseCase
import dsm.pick2024.domain.classroom.port.`in`.ClassroomFinderUseCase
import dsm.pick2024.domain.classroom.port.out.DeleteClassRoomPort
import dsm.pick2024.domain.classroom.port.out.SaveClassRoomPort
import dsm.pick2024.domain.classroom.presentation.dto.request.ClassroomStatusRequest
import dsm.pick2024.domain.main.port.`in`.MainUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChangeClassroomStatusService(
    private val deleteClassRoomPort: DeleteClassRoomPort,
    private val saveClassRoomPort: SaveClassRoomPort,
    private val attendanceFinderUseCase: AttendanceFinderUseCase,
    private val saveAttendancePort: SaveAttendancePort,
    private val classroomFinderUseCase: ClassroomFinderUseCase,
    private val mainUseCase: MainUseCase
) : ChangeClassroomStatusUseCase {
    @Transactional
    override fun changeClassroomStatus(request: ClassroomStatusRequest) {
        if (request.status == NO) {
            for (id in request.idList) {
                val classroom = classroomFinderUseCase.findByUserIdOrThrow(id)
                deleteClassRoomPort.deleteByUserId(classroom.userId)
                mainUseCase.sendEvent(classroom.userId)
            }
        } else {
            val update = mutableListOf<Classroom>()
            val updateAttendanceList = mutableListOf<Attendance>()

            request.idList.forEach { id ->
                val classroom = classroomFinderUseCase.findByUserIdOrThrow(id)

                val updatedClassroom = classroom.copy(status = OK)
                update.add(updatedClassroom)

                val updatedAttendance = attendanceFinderUseCase.findByUserIdOrThrow(classroom.userId).run {
                    copy(
                        period6 = getStatus(classroom, period6, 6),
                        period7 = getStatus(classroom, period7, 7),
                        period8 = getStatus(classroom, period8, 8),
                        period9 = getStatus(classroom, period9, 9),
                        period10 = getStatus(classroom, period10, 10)
                    )
                }

                updateAttendanceList.add(updatedAttendance)
            }

            saveClassRoomPort.saveAll(update)
            saveAttendancePort.saveAll(updateAttendanceList)
            request.idList.forEach { id ->
                mainUseCase.sendEvent(id)
            }
        }
    }

    private fun getStatus(
        classroom: Classroom,
        status: AttendanceStatus,
        period: Int
    ): AttendanceStatus {
        return if (period in classroom.startPeriod..classroom.endPeriod) {
            AttendanceStatus.MOVEMENT
        } else {
            status
        }
    }
}
