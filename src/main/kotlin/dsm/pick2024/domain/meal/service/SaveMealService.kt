package dsm.pick2024.domain.meal.service

import dsm.pick2024.domain.meal.entity.MealJpaEntity
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
            .mapNotNull { (date, mealInfos) ->
                if (mealInfos.any {
                    !it.breakfast.isNullOrEmpty() || !it.lunch.isNullOrEmpty() || !it.dinner.isNullOrEmpty()
                }
                ) {
                    val breakfast = stickMeal(mealInfos.map { it.breakfast })
                    val lunch = stickMeal(mealInfos.map { it.lunch })
                    val dinner = stickMeal(mealInfos.map { it.dinner })

                    val mealJpaEntity =
                        MealJpaEntity(
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

    override fun stickMeal(meals: List<String?>) = meals.filterNotNull().distinct().joinToString("||")
}
