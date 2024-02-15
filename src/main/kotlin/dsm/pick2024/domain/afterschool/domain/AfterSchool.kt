package dsm.pick2024.domain.afterschool.domain

import java.util.UUID

data class AfterSchool(
    val id: UUID? = null,
    val name: String,
    val grade: Int,
    val classNum: Int,
    val num: Int,
)
