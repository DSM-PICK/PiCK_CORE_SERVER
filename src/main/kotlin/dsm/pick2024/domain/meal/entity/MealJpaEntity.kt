package dsm.pick2024.domain.meal.entity

import dsm.pick2024.domain.meal.enum.MealType
import dsm.pick2024.global.base.BaseUUIDEntity
import java.time.LocalDate
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity(name = "tbl_meal")
class MealJpaEntity(
    id: UUID?,

    @Column(nullable = false, columnDefinition = "DATE")
    val mealDate: LocalDate,

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = true, columnDefinition = "TEXT")
    val mealType: MealType?,

    val menu: String? = null,

    val cal: String?

) : BaseUUIDEntity(id)
