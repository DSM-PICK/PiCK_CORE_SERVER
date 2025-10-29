package dsm.pick2024.domain.weekendmeal.entity

import dsm.pick2024.domain.admin.entity.AdminJpaEntity
import dsm.pick2024.global.base.BaseUUIDEntity
import java.time.LocalDate
import java.time.Month
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity(name = "tbl_weekend_meal_period")
class WeekendMealPeriodJpaEntity(
    id: UUID?,
    val start: LocalDate,
    val end: LocalDate,
    val month: Month,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    val admin: AdminJpaEntity
) : BaseUUIDEntity(id)
