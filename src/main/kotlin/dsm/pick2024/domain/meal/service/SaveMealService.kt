package dsm.pick2024.domain.meal.service

import dsm.pick2024.domain.meal.domain.Meal
import dsm.pick2024.domain.meal.port.`in`.MealUseCase
import dsm.pick2024.domain.meal.port.out.SaveMealPort
import dsm.pick2024.infrastructure.feign.NeisMealFeignClientService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SaveMealService(
    private val saveMealPort: SaveMealPort,
    private val neisMealFeignClientService: NeisMealFeignClientService
) : MealUseCase {
    @Transactional
    override fun saveNeisInfoToDatabase() {
        val mealForSave = neisMealFeignClientService.getNeisInfoToEntity()

        mealForSave
            .groupBy { it.mealDate }
            .forEach { (date, mealInfos) ->
                val mealMap = mealInfos.groupBy { it.mealType }

                mealMap.forEach { (mealType, mealsOfType) ->
                    val menu = stickMeal(mealsOfType.map { it.menu })
                    val cal = mealsOfType.map { it.cal }[0]//mealsOfType.firstNotNullOfOrNull { it.cal }
                    if (menu.isNotEmpty()) {
                        saveMealPort.save(
                            Meal(
                                mealDate = date,
                                mealType = mealType,
                                menu = menu,
                                cal = cal
                            )
                        )
                    }
                }
            }
    }

    override fun stickMeal(meals: List<String?>) =
        meals.filterNotNull().distinct().joinToString("||")
}
