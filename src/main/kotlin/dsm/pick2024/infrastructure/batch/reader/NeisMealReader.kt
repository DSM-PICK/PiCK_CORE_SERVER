package dsm.pick2024.infrastructure.batch.reader

import dsm.pick2024.domain.meal.domain.Meal
import dsm.pick2024.infrastructure.feign.NeisMealFeignClientService
import dsm.pick2024.infrastructure.feign.client.dto.response.NeisMealRow
import org.springframework.batch.item.ItemReader
import org.springframework.stereotype.Component

@Component
class NeisMealReader(
    private val neisMealFeignClientService: NeisMealFeignClientService
) : ItemReader<NeisMealRow> {

    private var meals: List<Meal> = emptyList()
    private var currentIndex: Int = 0


    override fun read(): NeisMealRow? {
        val meals = neisMealFeignClientService.getNeisInfoToEntity()
        return if (currentIndex < meals.size) {
            createNeisMealRow(meals[currentIndex++])
        } else {
            null
        }
    }

    private fun createNeisMealRow(meal: Meal): NeisMealRow {
        return NeisMealRow(
            MMEAL_SC_CODE = meal.mealType?.name.toString(),
            MLSV_YMD = meal.mealDate.toString(),
            DDISH_NM = meal.menu ?: "",
            CAL_INFO = meal.cal.toString()
        )
    }
}
