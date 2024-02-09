package dsm.pick2024.domain.application.domain

import dsm.pick2024.domain.application.enums.Status
import java.util.UUID

data class WeekendMeal(
    val id: UUID? = null,
    val username: String,
    val status: Status,
    val isCheck: Boolean,
)
