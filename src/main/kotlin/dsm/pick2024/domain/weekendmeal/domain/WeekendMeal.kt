package dsm.pick2024.domain.weekendmeal.domain

import dsm.pick2024.domain.weekendmeal.enums.Status
import java.util.UUID

data class WeekendMeal(
    val id: UUID? = null,
    val userId: UUID,
    val username: String,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val status: Status,
)
