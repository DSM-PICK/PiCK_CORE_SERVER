package dsm.pick2024.domain.classroom.port.out

import dsm.pick2024.domain.classroom.domain.Classroom

interface SaveClassRoomPort {
    fun save(classroom: Classroom)

    fun saveAll(classroom: List<Classroom>)
}
