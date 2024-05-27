package dsm.pick2024.domain.schedule.service

import dsm.pick2024.domain.schedule.port.`in`.ScheduleUseCase
import dsm.pick2024.domain.schedule.port.out.SaveFeignSchedulePort
import dsm.pick2024.infrastructure.feign.NeisScheduleFeignClientService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SaveScheduleService(
    private val saveFeignSchedulePort: SaveFeignSchedulePort,
    private val neisScheduleFeignClientService: NeisScheduleFeignClientService
) : ScheduleUseCase {

    @Transactional
    override fun saveNeisInfoToDatabase(start: String, end: String) {
        val scheduleForSave =
            neisScheduleFeignClientService.getNeisInfoToEntity(start, end)

        scheduleForSave?.let { saveFeignSchedulePort.saveFeignSchedule(it) }
    }
}
