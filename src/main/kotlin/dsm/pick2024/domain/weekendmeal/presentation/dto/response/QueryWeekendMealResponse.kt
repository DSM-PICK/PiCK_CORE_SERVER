package dsm.pick2024.domain.weekendmeal.presentation.dto.response

import dsm.pick2024.domain.weekendmeal.enums.Status
import java.util.UUID

data class QueryWeekendMealResponse (
    val id: UUID,
    val name: String,
    val status: Status,
    val grade: Int,
    val classNum: Int
)
