package dsm.pick2024.domain.classroom.service

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
    override fun queryFloorClassroom(floor: Int) =
        queryFloorClassroomPort.queryFloorClassroom(floor)
            .map {
                    it ->
                QueryClassroomResponse(
                    it.username,
                    it.classroomName,
                    move = "${it.grade}-${it.classNum}",
                    it.grade,
                    it.classNum,
                    it.num
                )
            }
}
