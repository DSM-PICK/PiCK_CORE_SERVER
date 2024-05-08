package dsm.pick2024.domain.selfstudy.domain

import dsm.pick2024.global.annotation.Aggregate
import java.time.LocalDate
import java.util.UUID

@Aggregate
data class SelfStudy(
    val id: UUID? = null,
    val floor: Int,
    var teacher: String,
    val date: LocalDate
)
