package dsm.pick2024.domain.classroom.port.out

import dsm.pick2024.domain.classroom.domain.Classroom
import java.util.*

interface FindByUserIdPort {
    fun findByUserId(userId: UUID): Classroom?
}
