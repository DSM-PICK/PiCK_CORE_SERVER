package dsm.pick2024.domain.schedule.service

import dsm.pick2024.domain.schedule.port.`in`.SaveScheduleUseCase
import dsm.pick2024.domain.schedule.port.out.DeleteSchedulePort
import dsm.pick2024.domain.schedule.port.out.SaveSchedulePort
import dsm.pick2024.global.config.cache.CacheName
import dsm.pick2024.infrastructure.feign.NeisScheduleFeignClientService
import org.springframework.cache.annotation.CacheEvict
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class SaveScheduleService(
    private val saveSchedulePort: SaveSchedulePort,
    private val deleteSchedulePort: DeleteSchedulePort,
    private val neisScheduleFeignClientService: NeisScheduleFeignClientService
) : SaveScheduleUseCase {

    @CacheEvict(value = [CacheName.SCHEDULES], allEntries = true)
    @Transactional
    override fun saveNeisInfoToDatabase() {

        val (start, end) = formatDate()
        deleteSchedulePort.deleteAll()
        val scheduleForSave =
            neisScheduleFeignClientService.getNeisInfoToEntity(start, end)

        scheduleForSave?.let { saveSchedulePort.saveFeignSchedule(it) }
    }

    private fun formatDate(): Pair<String, String> {
        val today = LocalDate.now()

        val start = LocalDate.of(today.year - 1, 1, 1)

        val end = LocalDate.of(today.year + 1, 3, 1)

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        return start.format(formatter) to end.format(formatter)
    }
}
