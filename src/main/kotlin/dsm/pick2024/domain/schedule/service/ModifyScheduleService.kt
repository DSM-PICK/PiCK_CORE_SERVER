package dsm.pick2024.domain.schedule.service

import dsm.pick2024.domain.schedule.exception.ScheduleNotFoundException
import dsm.pick2024.domain.schedule.port.`in`.ModifyScheduleUseCase
import dsm.pick2024.domain.schedule.port.out.QuerySchedulePort
import dsm.pick2024.domain.schedule.port.out.SaveSchedulePort
import dsm.pick2024.domain.schedule.presentation.dto.request.ModifyScheduleRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ModifyScheduleService(
    private val saveSchedulePort: SaveSchedulePort,
    private val querySchedulePort: QuerySchedulePort
) : ModifyScheduleUseCase {

    @Transactional
    override fun modifyModify(request: ModifyScheduleRequest) {
        val schedule = querySchedulePort.findById(request.id)
            ?: throw ScheduleNotFoundException

        saveSchedulePort.save(
            schedule.copy(
                eventName = request.eventName
            )
        )
    }
}
