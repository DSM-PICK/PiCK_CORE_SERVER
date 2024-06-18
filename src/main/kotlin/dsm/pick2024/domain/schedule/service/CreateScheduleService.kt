package dsm.pick2024.domain.schedule.service

import dsm.pick2024.domain.schedule.domain.Schedule
import dsm.pick2024.domain.schedule.port.`in`.CreateScheduleUseCase
import dsm.pick2024.domain.schedule.port.out.SaveSchedulePort
import dsm.pick2024.domain.schedule.presentation.dto.request.CreateScheduleRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateScheduleService(
    private val saveSchedulePort: SaveSchedulePort
) : CreateScheduleUseCase {

    @Transactional
    override fun createSchedule(request: CreateScheduleRequest) {
        saveSchedulePort.save(
            Schedule(
                eventName = request.eventName,
                date = request.date
            )
        )
    }
}
