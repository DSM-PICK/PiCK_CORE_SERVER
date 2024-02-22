package dsm.pick2024.domain.afterschool.domain

import dsm.pick2024.domain.afterschool.enum.Status
import java.util.UUID

data class AfterSchoolStudent(
    val id: UUID? = null,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val name: String,
    val status: Status
)
