package dsm.pick2024.domain.classroom.port.out

import dsm.pick2024.domain.classroom.domain.Classroom

interface QueryAllClassroomPort {
    fun findAll(): List<Classroom>
}
