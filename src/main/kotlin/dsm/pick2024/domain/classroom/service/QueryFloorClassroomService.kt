package dsm.pick2024.domain.classroom.service

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.classroom.port.`in`.QueryFloorClassroomUseCase
import dsm.pick2024.domain.classroom.port.out.QueryFloorClassroomPort
import dsm.pick2024.domain.classroom.presentation.dto.response.QueryClassroomResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryFloorClassroomService(
    private val queryFloorClassroomPort: QueryFloorClassroomPort
) : QueryFloorClassroomUseCase {
    @Transactional(readOnly = true)
    override fun queryFloorClassroom(
        floor: Int,
        status: Status
    ) = queryFloorClassroomPort.queryFloorClassroom(floor)
        .filter { if (status == Status.QUIET) it.status == Status.QUIET else it.status == Status.OK }
        .map {
            QueryClassroomResponse(
                id = it.userId,
                username = it.username,
                classroomName = it.classroomName,
                move = "${it.grade}-${it.classNum}",
                grade = it.grade,
                classNum = it.classNum,
                num = it.num,
                startPeriod = it.startPeriod,
                endPeriod = it.endPeriod
            )
        }
}
