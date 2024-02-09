package dsm.pick2024.domain.classroom.port.out

import dsm.pick2024.domain.classroom.domain.Classroom

interface QueryFloorClassroomPort {
    fun queryFloorClassroom(floor: Int): List<Classroom>
}
