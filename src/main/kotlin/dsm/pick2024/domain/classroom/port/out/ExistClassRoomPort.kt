package dsm.pick2024.domain.classroom.port.out

import java.util.UUID

interface ExistClassRoomPort {
    fun existOKByUserId(userId: UUID): Boolean

    fun existsByUserId(userId: UUID): Boolean
}
