package dsm.pick2024.domain.weekendmeal.domain

import dsm.pick2024.domain.weekendmeal.enums.Status
import dsm.pick2024.global.annotation.Aggregate
import java.util.UUID

@Aggregate
data class WeekendMeal(
    val id: UUID = UUID(0,0),
    val userId: UUID,
    val userName: String,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val status: Status
)
