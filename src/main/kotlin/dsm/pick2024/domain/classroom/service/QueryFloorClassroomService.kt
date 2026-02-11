package dsm.pick2024.domain.classroom.service

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.attendance.port.`in`.AttendanceFinderUseCase
import dsm.pick2024.domain.classroom.exception.FloorNotFoundException
import dsm.pick2024.domain.classroom.port.`in`.QueryFloorClassroomUseCase
import dsm.pick2024.domain.classroom.port.out.QueryClassroomPort
import dsm.pick2024.domain.classroom.presentation.dto.response.QueryClassroomResponse
import dsm.pick2024.global.common.TimeUtils
import org.springframework.stereotype.Service
import java.time.DayOfWeek

@Service
class QueryFloorClassroomService(
    private val queryClassroomPort: QueryClassroomPort,
    private val attendanceFinderUseCase: AttendanceFinderUseCase
) : QueryFloorClassroomUseCase {

    override fun queryFloorClassroom(
        floor: Int,
        status: Status
    ): List<QueryClassroomResponse> {
        val today = TimeUtils.nowLocalDate().dayOfWeek

        val classrooms = when (floor) {
            2, 3, 4 -> {
                val filteredClassrooms = if (today == DayOfWeek.TUESDAY || today == DayOfWeek.FRIDAY) {
                    queryClassroomPort.queryFloorClassroomWithAttendance(floor)
                } else {
                    queryClassroomPort.queryFloorClassroom(floor)
                }
                filteredClassrooms.filter { it.status == status }
            }
            5 -> {
                queryClassroomPort.findAllByStatus(status)
            }
            else -> throw FloorNotFoundException
        }

        return classrooms.map { classroom ->
            val move = if (today == DayOfWeek.TUESDAY || today == DayOfWeek.FRIDAY) {
                attendanceFinderUseCase.findByUserIdOrThrow(classroom.userId).place ?: ""
            } else {
                "${classroom.grade}-${classroom.classNum}"
            }

            QueryClassroomResponse(
                userId = classroom.userId,
                userName = classroom.userName,
                classroomName = classroom.classroomName,
                move = move,
                grade = classroom.grade,
                classNum = classroom.classNum,
                num = classroom.num,
                start = classroom.startPeriod,
                end = classroom.endPeriod
            )
        }.distinctBy { it.userId }.sortedWith(compareBy({ it.grade }, { it.classNum }, { it.num }))
    }
}
