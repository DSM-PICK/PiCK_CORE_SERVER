package dsm.pick2024.domain.classroom.service

import dsm.pick2024.domain.classroom.port.`in`.QueryGradeClassroomUseCase
import dsm.pick2024.domain.classroom.port.out.QueryClassroomPort
import dsm.pick2024.domain.classroom.presentation.dto.response.QueryClassroomResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryGradeClassroomService(
    private val queryClassroomPort: QueryClassroomPort
) : QueryGradeClassroomUseCase {
    @Transactional(readOnly = true)
    override fun queryGradeClassroom(
        grade: Int,
        classNum: Int
    ) =
        queryClassroomPort.queryGradeClassroom(grade, classNum)
            .map {
                    it ->
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
            }
}
