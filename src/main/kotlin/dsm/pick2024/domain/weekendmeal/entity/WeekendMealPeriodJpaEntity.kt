package dsm.pick2024.domain.weekendmeal.entity

import dsm.pick2024.global.base.BaseUUIDEntity
import java.time.LocalDate
import java.time.Month
import java.util.UUID
import javax.persistence.Entity

@Entity(name = "tbl_weekend_meal_period")
class WeekendMealPeriodJpaEntity(
    id: UUID?,
    val start: LocalDate,
    val end: LocalDate,
    val month: Month,
    val adminId: UUID
) : BaseUUIDEntity(id)
