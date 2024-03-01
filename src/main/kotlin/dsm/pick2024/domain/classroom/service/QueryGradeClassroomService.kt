package dsm.pick2024.domain.classroom.service

import dsm.pick2024.domain.classroom.port.`in`.QueryGradeClassroomUseCase
import dsm.pick2024.domain.classroom.port.out.QueryGradeClassroomPort
import dsm.pick2024.domain.classroom.presentation.dto.response.QueryClassroomResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryGradeClassroomService(
    private val queryGradeClassroomPort: QueryGradeClassroomPort
) : QueryGradeClassroomUseCase {

    @Transactional(readOnly = true)
    override fun queryGradeClassroom(grade: Int, classNum: Int) =
        queryGradeClassroomPort.queryGradeClassroom(grade, classNum)
            .map {
                    it ->
                QueryClassroomResponse(
                    it.classroomName,
                    it.username,
                    move = "${it.grade}-${it.classNum}",
                    it.grade,
                    it.classNum,
                    it.num
                )
            }
}
