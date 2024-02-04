package dsm.pick2024.domain.classroom.port.out

interface ClassroomDeletePort {
    fun deleteByUsername(username: String)
}
