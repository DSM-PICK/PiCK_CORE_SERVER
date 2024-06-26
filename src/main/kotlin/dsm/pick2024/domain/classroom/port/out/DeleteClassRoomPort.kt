package dsm.pick2024.domain.classroom.port.out

import java.util.UUID

interface DeleteClassRoomPort {

    fun deleteByUserId(userId: UUID)

    fun deleteAll()
}
