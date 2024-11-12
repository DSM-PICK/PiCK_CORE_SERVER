package dsm.pick2024.domain.schedule.port.out

import java.util.UUID

interface DeleteSchedulePort {
    fun deleteById(id: UUID)

    fun deleteAll()
}
