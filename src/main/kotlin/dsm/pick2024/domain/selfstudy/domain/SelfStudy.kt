package dsm.pick2024.domain.selfstudy.domain

import dsm.pick2024.global.annotation.Aggregate
import java.time.LocalDate
import java.util.UUID

@Aggregate
data class SelfStudy(
    val id: UUID = UUID(0,0),
    val floor: Int,
    val teacherName: String,
    val date: LocalDate
)
