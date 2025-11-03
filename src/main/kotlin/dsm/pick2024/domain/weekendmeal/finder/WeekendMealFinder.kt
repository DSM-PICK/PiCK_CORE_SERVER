package dsm.pick2024.domain.weekendmeal.finder

import dsm.pick2024.domain.weekendmeal.exception.WeekendMealNotFoundException
import dsm.pick2024.domain.weekendmeal.port.`in`.WeekendMealFinderUseCase
import dsm.pick2024.domain.weekendmeal.port.out.QueryWeekendMealPort
import org.springframework.stereotype.Component
import java.util.*

@Component
class WeekendMealFinder(
    private val queryWeekendMealPort: QueryWeekendMealPort
) : WeekendMealFinderUseCase {
    override fun findByIdOrThrow(id: UUID) =
        queryWeekendMealPort.findById(id) ?: throw WeekendMealNotFoundException

    override fun findByUserIdOrThrow(id: UUID) =
        queryWeekendMealPort.findByUserId(id) ?: throw WeekendMealNotFoundException
}
