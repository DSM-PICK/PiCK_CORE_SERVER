package dsm.pick2024.domain.classroom.service

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.attendance.port.`in`.AttendanceFinderUseCase
import dsm.pick2024.domain.classroom.exception.FloorNotFoundException
import dsm.pick2024.domain.classroom.port.`in`.ClassroomFinderUseCase
import dsm.pick2024.domain.classroom.port.`in`.QueryFloorClassroomUseCase
import dsm.pick2024.domain.classroom.presentation.dto.response.QueryClassroomResponse
import org.joda.time.LocalDate
import org.springframework.stereotype.Service

@Service
class QueryFloorClassroomService(
    private val classroomFinderUseCase: ClassroomFinderUseCase,
    private val attendanceFinderUseCase: AttendanceFinderUseCase
) : QueryFloorClassroomUseCase {

    override fun queryFloorClassroom(
        floor: Int,
        status: Status
    ): List<QueryClassroomResponse> {
        val today = LocalDate.now().dayOfWeek

        val classrooms = when (floor) {
            2, 3, 4 -> {
                val filteredClassrooms = if (today == 2 || today == 5) {
                    classroomFinderUseCase.queryFloorClassroomWithAttendanceOrThrow(floor)
                } else {
                    classroomFinderUseCase.queryFloorClassroomOrThrow(floor)
                }
                filteredClassrooms.filter { it.status == status }
            }
            5 -> {
                classroomFinderUseCase.findAllByStatusOrThrow(status)
            }
            else -> throw FloorNotFoundException
        }

        return classrooms.map { classroom ->
            val move = if (today == 2 || today == 5) {
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
