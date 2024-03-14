package dsm.pick2024.domain.schedule.service

import dsm.pick2024.domain.schedule.port.`in`.ModifyScheduleUseCase
import dsm.pick2024.domain.schedule.port.out.FindScheduleByIdPort
import dsm.pick2024.domain.schedule.port.out.ScheduleSavePort
import dsm.pick2024.domain.schedule.presentation.dto.request.ModifyScheduleRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ModifyScheduleService(
    private val scheduleSavePort: ScheduleSavePort,
    private val findScheduleByIdPort: FindScheduleByIdPort
) : ModifyScheduleUseCase {

    @Transactional
    override fun modifyModify(request: ModifyScheduleRequest) {
        val schedule = findScheduleByIdPort.findById(request.id)
            ?: throw RuntimeException("")

        scheduleSavePort.save(
            schedule.copy(
                eventName = request.eventName
            )
        )
    }
}
