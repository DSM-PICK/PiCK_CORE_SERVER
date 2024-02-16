package dsm.pick2024.domain.afterschool.domain

import java.util.UUID

data class AfterSchool(
    val id: UUID? = null,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val name: String,
)
