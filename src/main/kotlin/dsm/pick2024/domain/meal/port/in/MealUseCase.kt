package dsm.pick2024.domain.meal.port.`in`

interface MealUseCase {
    fun saveNeisInfoToDatabase()
    fun stickMeal(meals: List<String?>): String
}
