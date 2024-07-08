package dsm.pick2024.domain.classroom.service

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.attendance.port.out.QueryAttendancePort
import dsm.pick2024.domain.classroom.exception.FloorNotFoundException
import dsm.pick2024.domain.classroom.port.`in`.QueryFloorClassroomUseCase
import dsm.pick2024.domain.classroom.port.out.QueryClassroomPort
import dsm.pick2024.domain.classroom.presentation.dto.response.QueryClassroomResponse
import org.joda.time.LocalDate
import org.springframework.stereotype.Service

@Service
class QueryFloorClassroomService(
    private val queryClassroomPort: QueryClassroomPort,
    private val queryAttendancePort: QueryAttendancePort
) : QueryFloorClassroomUseCase {

    override fun queryFloorClassroom(
        floor: Int,
        status: Status
    ): List<QueryClassroomResponse> {
        when (floor) {
            2, 3, 4, 5 -> {
                val today = LocalDate.now().dayOfWeek

                val dayFilter = if (today == 2 || today == 5) {
                    queryClassroomPort.queryFloorClassroomWithAttendance(floor)
                        .filter { it.status == if (status == Status.QUIET) Status.QUIET else Status.OK }
                } else {
                    queryClassroomPort.queryFloorClassroom(floor)
                        .filter { it.status == if (status == Status.QUIET) Status.QUIET else Status.OK }
                }

                return dayFilter.map { classroom ->
                    val move = if (today == 2 || today == 5) {
                        queryAttendancePort.findByUserId(classroom.userId)?.place ?: ""
                    } else {
                        "${classroom.grade}-${classroom.classNum}"
                    }

                    QueryClassroomResponse(
                        id = classroom.userId,
                        username = classroom.userName,
                        classroomName = classroom.classroomName,
                        move = move,
                        grade = classroom.grade,
                        classNum = classroom.classNum,
                        num = classroom.num,
                        startPeriod = classroom.startPeriod,
                        endPeriod = classroom.endPeriod
                    )
                }.sortedWith(compareBy({ it.grade }, { it.classNum }, { it.num }))
            }

            else -> {
                throw FloorNotFoundException
            }
        }
    }
}
