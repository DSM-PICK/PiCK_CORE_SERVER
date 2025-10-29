package dsm.pick2024.domain.weekendmeal.service

import dsm.pick2024.domain.weekendmeal.port.`in`.QueryAllWeekendMealStatusUseCase
import dsm.pick2024.domain.weekendmeal.port.`in`.WeekendMealFinderUseCase
import dsm.pick2024.domain.weekendmeal.presentation.dto.response.QueryWeekendMealResponse
import org.springframework.stereotype.Service

@Service
class QueryAllWeekendMealStatus(
    private val weekendMealFinderUseCase: WeekendMealFinderUseCase
) : QueryAllWeekendMealStatusUseCase {

    override fun findAll(): List<QueryWeekendMealResponse> {
        val weekendMeal = weekendMealFinderUseCase.findAllOrThrow()

        val responseList =
            weekendMeal.map { meal ->
                QueryWeekendMealResponse(
                    id = meal.id,
                    userName = meal.userName,
                    status = meal.status,
                    grade = meal.grade,
                    classNum = meal.classNum,
                    num = meal.num
                )
            }.distinctBy { it.id }.sortedWith(compareBy({ it.grade }, { it.classNum }, { it.num }))

        return responseList
    }
}
