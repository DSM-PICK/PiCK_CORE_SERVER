package dsm.pick2024.domain.schedule.port.`in`

import java.util.UUID

interface DeleteScheduleUseCase {
    fun deleteSchedule(id: UUID)
}
