package dsm.pick2024.domain.classroom.service

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.classroom.port.`in`.QueryAllClassroomUseCase
import dsm.pick2024.domain.classroom.port.out.QueryClassroomPort
import dsm.pick2024.domain.classroom.presentation.dto.response.QueryClassroomResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryAllClassroomService(
    private val queryClassroomPort: QueryClassroomPort
): QueryAllClassroomUseCase {
    @Transactional(readOnly = true)
    override fun queryAllClassroom(status: Status): List<QueryClassroomResponse> =
        queryClassroomPort.findAllByStatus(status).map {
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
