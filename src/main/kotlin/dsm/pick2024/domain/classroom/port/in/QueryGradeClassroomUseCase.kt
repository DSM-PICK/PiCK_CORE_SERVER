package dsm.pick2024.domain.classroom.port.`in`

import dsm.pick2024.domain.classroom.presentation.dto.response.QueryClassroomResponse

interface QueryGradeClassroomUseCase {
    fun queryGradeClassroom(
        grade: Int,
        classNum: Int
    ): List<QueryClassroomResponse>
}
