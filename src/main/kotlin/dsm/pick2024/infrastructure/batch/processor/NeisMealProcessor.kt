package dsm.pick2024.infrastructure.batch.processor

import dsm.pick2024.domain.meal.domain.Meal
import dsm.pick2024.domain.meal.enum.MealType
import dsm.pick2024.infrastructure.feign.client.dto.response.NeisMealRow
import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class NeisMealProcessor : ItemProcessor<NeisMealRow, Meal> {

    override fun process(item: NeisMealRow): Meal {
        val mealDate = LocalDate.parse(item.MLSV_YMD)
        val mealType = getMealType(item.MMEAL_SC_CODE)
        val menu = item.DDISH_NM.replace("<br/>", "||").replace(Regex("[0-9.()]"), "").trim()
        val cal = item.CAL_INFO

        return Meal(
            mealDate = mealDate,
            mealType = mealType,
            menu = menu,
            cal = cal
        )
    }

    private fun getMealType(mealCode: String): MealType? {
        return when (mealCode) {
            "BREAKFAST" -> MealType.BREAKFAST
            "LUNCH" -> MealType.LUNCH
            "DINNER" -> MealType.DINNER
            else -> null
        }
    }
}
