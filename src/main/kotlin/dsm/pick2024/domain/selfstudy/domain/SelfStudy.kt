package dsm.pick2024.domain.selfstudy.domain

import java.time.LocalDate
import java.util.UUID

data class SelfStudy (
    val id: UUID? = null,
    val floor: Int,
    val teacher: String,
    val date: LocalDate
)
