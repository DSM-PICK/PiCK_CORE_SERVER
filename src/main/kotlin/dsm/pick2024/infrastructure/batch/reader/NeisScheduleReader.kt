package dsm.pick2024.infrastructure.batch.reader

import dsm.pick2024.domain.schedule.domain.Schedule
import dsm.pick2024.infrastructure.feign.NeisScheduleFeignClientService
import org.springframework.batch.item.ItemReader
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class NeisScheduleReader(
    private val neisScheduleFeignClientService: NeisScheduleFeignClientService
) : ItemReader<List<Schedule>>{

    override fun read(): List<Schedule>? {
        val start = LocalDate.now().minusDays(30)
        val end = LocalDate.now()
        return neisScheduleFeignClientService.getNeisInfoToEntity(start.toString(), end.toString())
    }
}
