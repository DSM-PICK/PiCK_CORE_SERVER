package dsm.pick2024.infrastructure.batch.writer

import dsm.pick2024.domain.meal.domain.Meal
import dsm.pick2024.domain.meal.port.out.SaveMealPort
import org.springframework.batch.item.ItemWriter
import org.springframework.stereotype.Component

@Component
class MealItemWriter(
    private val saveMealPort: SaveMealPort
) : ItemWriter<Meal> {

    override fun write(items: List<Meal>) {
        saveMealPort.saveAll(items)
    }
}
