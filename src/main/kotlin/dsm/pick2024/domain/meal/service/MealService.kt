package dsm.pick2024.domain.meal.service

import dsm.pick2024.domain.meal.entity.MealJpaEntity
import dsm.pick2024.domain.meal.port.out.SaveMealPort
import dsm.pick2024.infrastructure.feign.NeisMealFeignClientService
import org.springframework.stereotype.Service

@Service
class MealService(
    private val saveMealPort: SaveMealPort,
    private val neisMealFeignClientService: NeisMealFeignClientService
) {

    fun saveNeisInfoToDatabase() {
        val mealForSave = neisMealFeignClientService.getNeisInfoToEntity()

        mealForSave
            .groupBy { it.mealDate }
            .mapNotNull { (date, mealInfos) ->
                if (mealInfos.any { !it.breakfast.isNullOrEmpty() || !it.lunch.isNullOrEmpty() || !it.dinner.isNullOrEmpty() }) {
                    val breakfast = stickMeal(mealInfos.map { it.breakfast })
                    val lunch = stickMeal(mealInfos.map { it.lunch })
                    val dinner = stickMeal(mealInfos.map { it.dinner })

                    val mealJpaEntity = MealJpaEntity(
                        id = null,
                        mealDate = date,
                        breakfast = breakfast,
                        lunch = lunch,
                        dinner = dinner
                    )
                    saveMealPort.save(mealJpaEntity)
                } else {
                    throw InternalError()
                }
            }
    }

    private fun stickMeal(meals: List<String?>): String {
        return meals.filterNotNull().distinct().joinToString("||")
    }
}
