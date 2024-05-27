package dsm.pick2024.domain.meal.entity

import dsm.pick2024.global.base.BaseUUIDEntity
import java.time.LocalDate
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "tbl_meal")
class MealJpaEntity(
    id: UUID?,

    @Column(nullable = false, columnDefinition = "DATE")
    val mealDate: LocalDate,

    @Column(nullable = true, columnDefinition = "TEXT")
    val breakfast: String?,

    @Column(nullable = true, columnDefinition = "TEXT")
    val lunch: String?,

    @Column(nullable = true, columnDefinition = "TEXT")
    val dinner: String?

) : BaseUUIDEntity(id)
