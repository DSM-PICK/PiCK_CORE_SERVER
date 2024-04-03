package dsm.pick2024.domain.classroom.port.out

import java.util.UUID

interface ExistOKByUserIdPort {
    fun existOKByUserId(userId: UUID): Boolean
}
