package dsm.pick2024.domain.classroom.port.out

import dsm.pick2024.domain.classroom.domain.Classroom

interface QueryGradeClassroomPort {
    fun queryGradeClassroom(grade: Int, classNum: Int): List<Classroom>
}
