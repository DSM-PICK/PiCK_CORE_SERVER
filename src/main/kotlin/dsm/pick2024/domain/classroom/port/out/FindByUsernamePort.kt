package dsm.pick2024.domain.classroom.port.out

import dsm.pick2024.domain.classroom.domain.Classroom

interface FindByUsernamePort {
    fun findByUsername(username: String): Classroom?
}
