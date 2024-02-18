package dsm.pick2024.domain.classroom.port.`in`

import dsm.pick2024.domain.classroom.presentation.dto.response.QueryClassroomResponse

interface QueryFloorClassroomUseCase {
    fun queryFloorClassroom(floor: Int): List<QueryClassroomResponse>
}
