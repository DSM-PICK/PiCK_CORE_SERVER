package dsm.pick2024.domain.meal.service

import dsm.pick2024.domain.meal.domain.Meal
import dsm.pick2024.domain.meal.port.`in`.RetryMealUseCase
import dsm.pick2024.domain.meal.port.out.MealPort
import dsm.pick2024.global.common.TimeUtils
import dsm.pick2024.infrastructure.feign.neis.NeisMealFeignClientService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.YearMonth

@Service
class RetryMealService(
    private val mealPort: MealPort,
    private val neisMealFeignClientService: NeisMealFeignClientService
) : RetryMealUseCase {

    @Transactional
    override fun execute() {
        val currentMonth = YearMonth.from(TimeUtils.nowLocalDate())

        mealPort.deleteByYearMonth(currentMonth)

        val mealForSave = neisMealFeignClientService.getNeisInfoToEntityByYearMonth(currentMonth)

        mealForSave
            .groupBy { it.mealDate }
            .forEach { (date, mealInfos) ->
                val mealMap = mealInfos.groupBy { it.mealType }

                mealMap.forEach { (mealType, mealsOfType) ->
                    val menu = mealsOfType.map { it.menu }.filterNotNull().distinct().joinToString("||")
                    val cal = mealsOfType.map { it.cal }.get(0)

                    if (menu.isNotEmpty()) {
                        mealPort.save(
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
}
