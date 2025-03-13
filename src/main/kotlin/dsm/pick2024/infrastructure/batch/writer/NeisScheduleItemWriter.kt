package dsm.pick2024.infrastructure.batch.writer

import dsm.pick2024.domain.schedule.domain.Schedule
import dsm.pick2024.domain.schedule.port.out.SaveSchedulePort
import org.springframework.batch.item.ItemWriter
import org.springframework.stereotype.Component

@Component
class NeisScheduleItemWriter(
    private val saveSchedulePort: SaveSchedulePort
) : ItemWriter<List<Schedule>> {

    override fun write(items: MutableList<out List<Schedule>>) {
        items.forEach { saveSchedulePort.saveFeignSchedule(it) }
    }
}
