package dsm.pick2024.domain.weekendmeal.service

import dsm.pick2024.domain.weekendmeal.port.`in`.UpdateWeekendMealUseCase
import dsm.pick2024.domain.weekendmeal.port.out.UpdateWeekendMealPort
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class UpdateWeekendMealService(
    private val updateWeekendMealPort: UpdateWeekendMealPort
    ): UpdateWeekendMealUseCase {

    @Transactional
    override fun execute(){
        updateWeekendMealPort.resetStatus()
    }

}
