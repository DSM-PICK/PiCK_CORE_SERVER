package dsm.pick2024.domain.weekendmeal.service

import dsm.pick2024.domain.weekendmeal.port.`in`.QueryAllWeekendMealStatusUseCase
import dsm.pick2024.domain.weekendmeal.port.out.QueryWeekendMealPort
import dsm.pick2024.domain.weekendmeal.presentation.dto.response.QueryWeekendMealResponse
import org.springframework.stereotype.Service

@Service
class QueryAllWeekendMealStatus(
    private val queryWeekendMealPort: QueryWeekendMealPort
) : QueryAllWeekendMealStatusUseCase {

    override fun findAll(): List<QueryWeekendMealResponse> {
        val weekendMeal = queryWeekendMealPort.findAll()

        if (weekendMeal.isEmpty()) return emptyList()

        val responseList =
            weekendMeal.map { meal ->
                QueryWeekendMealResponse(
                    id = meal.id!!,
                    name = meal.userName,
                    status = meal.status,
                    grade = meal.grade,
                    classNum = meal.classNum,
                    num = meal.num
                )
            }

        return responseList
    }
}
