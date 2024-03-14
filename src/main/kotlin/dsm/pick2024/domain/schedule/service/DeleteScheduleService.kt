package dsm.pick2024.domain.schedule.service

import dsm.pick2024.domain.schedule.port.`in`.DeleteScheduleUseCase
import dsm.pick2024.domain.schedule.port.out.DeleteSchedulePort
import dsm.pick2024.domain.schedule.port.out.FindScheduleByIdPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class DeleteScheduleService(
    private val deleteSchedulePort: DeleteSchedulePort,
    private val findScheduleByIdPort: FindScheduleByIdPort
) : DeleteScheduleUseCase {

    @Transactional
    override fun deleteSchedule(id: UUID) {
        findScheduleByIdPort.findById(id)?.let { deleteSchedulePort.deleteById(id) }
    }
}
