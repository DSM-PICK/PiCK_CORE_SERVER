package dsm.pick2024.domain.schedule.service

import dsm.pick2024.domain.schedule.port.`in`.DeleteScheduleUseCase
import dsm.pick2024.domain.schedule.port.out.DeleteSchedulePort
import dsm.pick2024.domain.schedule.port.out.QuerySchedulePort
import org.springframework.cache.annotation.CacheEvict
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class DeleteScheduleService(
    private val deleteSchedulePort: DeleteSchedulePort,
    private val querySchedulePort: QuerySchedulePort
) : DeleteScheduleUseCase {

    @CacheEvict(value = ["schedules"], allEntries = true)
    @Transactional
    override fun deleteSchedule(id: UUID) {
        querySchedulePort.findById(id)?.let { deleteSchedulePort.deleteById(id) }
    }
}
