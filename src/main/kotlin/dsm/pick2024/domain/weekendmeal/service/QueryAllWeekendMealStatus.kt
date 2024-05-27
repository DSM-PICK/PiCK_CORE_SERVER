package dsm.pick2024.domain.weekendmeal.service

import dsm.pick2024.domain.weekendmeal.port.`in`.QueryAllWeekendMealStatusUseCase
import dsm.pick2024.domain.weekendmeal.port.out.FindAllWeekendMealStatusPort
import dsm.pick2024.domain.weekendmeal.presentation.dto.response.QueryWeekendMealResponse
import org.springframework.stereotype.Service

@Service
class QueryAllWeekendMealStatus(
    private val findAllWeekendMealStatusPort: FindAllWeekendMealStatusPort
) : QueryAllWeekendMealStatusUseCase {

    override fun findAll(): List<QueryWeekendMealResponse> {
        val weekendMeal = findAllWeekendMealStatusPort.findAll()

        val responseList =
            weekendMeal.map { meal ->
                QueryWeekendMealResponse(
                    id = meal.userId,
                    name = meal.username,
                    status = meal.status,
                    grade = meal.grade,
                    classNum = meal.classNum,
                    num = meal.num
                )
            }

        return responseList
    }
}
