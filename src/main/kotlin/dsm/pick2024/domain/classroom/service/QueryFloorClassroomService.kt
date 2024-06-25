package dsm.pick2024.domain.classroom.service

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.classroom.port.`in`.QueryFloorClassroomUseCase
import dsm.pick2024.domain.classroom.port.out.QueryClassroomPort
import dsm.pick2024.domain.classroom.presentation.dto.response.QueryClassroomResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryFloorClassroomService(
    private val queryClassroomPort: QueryClassroomPort
) : QueryFloorClassroomUseCase {

    @Transactional(readOnly = true)
    override fun queryFloorClassroom(
        floor: Int,
        status: Status
    ): List<QueryClassroomResponse> {
        val classrooms = if (floor == 5) {
            queryClassroomPort.findAllByStatus(status)
        } else {
            queryClassroomPort.queryFloorClassroom(floor)
                .filter { it.status == if (status == Status.QUIET) Status.QUIET else Status.OK }
        }

        return classrooms.map {
            QueryClassroomResponse(
                id = it.userId,
                username = it.userName,
                classroomName = it.classroomName,
                move = "${it.grade}-${it.classNum}",
                grade = it.grade,
                classNum = it.classNum,
                num = it.num,
                startPeriod = it.startPeriod,
                endPeriod = it.endPeriod
            )
        }.sortedWith(compareBy({ it.grade }, { it.classNum }, { it.num }))
    }
}
