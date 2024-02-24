package dsm.pick2024.domain.application.entity

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.global.base.BaseUUIDEntity
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity(name = "tbl_weekend_meal")
class WeekendMealJpaEntity(
    id: UUID?,
    val userId: UUID,
    val grade: Int,
    val classNum: Int,
    @Enumerated(value = EnumType.STRING)
    val status: Status = Status.QUIET
) : BaseUUIDEntity(id)
