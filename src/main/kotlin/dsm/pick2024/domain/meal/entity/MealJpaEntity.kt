package dsm.pick2024.domain.meal.entity

import dsm.pick2024.global.base.BaseUUIDEntity
import java.time.LocalDate
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "tbl_meal")
class MealJpaEntity(
    id: UUID?,

    @Column(columnDefinition = "DATE", nullable = false)
    val mealDate: LocalDate,

    @Column(columnDefinition = "VARCHAR(255)")
    val breakfast: String,

    @Column(columnDefinition = "VARCHAR(255)")
    val lunch: String,

    @Column(columnDefinition = "VARCHAR(255)")
    val dinner: String

) : BaseUUIDEntity(id)
