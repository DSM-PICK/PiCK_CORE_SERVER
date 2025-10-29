package dsm.pick2024.domain.weekendmeal.finder

import dsm.pick2024.domain.weekendmeal.enums.Status
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

    override fun findByGradeAndClassNumOrThrow(grade: Int, classNum: Int) =
        queryWeekendMealPort.findByGradeAndClassNum(grade, classNum) ?: throw WeekendMealNotFoundException

    override fun findByStatusOrThrow(status: Status) =
        queryWeekendMealPort.findByStatus(status) ?: throw WeekendMealNotFoundException

    override fun findAllOrThrow() =
        queryWeekendMealPort.findAll() ?: throw WeekendMealNotFoundException

    override fun findByUserIdOrThrow(id: UUID) =
        queryWeekendMealPort.findByUserId(id) ?: throw WeekendMealNotFoundException

    override fun findByGradeAndClassNumAndStatusOrThrow(grade: Int, classNum: Int, status: Status) =
        queryWeekendMealPort.findByGradeAndClassNumAndStatus(grade, classNum, status)
            ?: throw WeekendMealNotFoundException
}
