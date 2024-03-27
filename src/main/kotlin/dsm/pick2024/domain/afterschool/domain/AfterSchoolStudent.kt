package dsm.pick2024.domain.afterschool.domain

import dsm.pick2024.domain.afterschool.enums.Status
import java.util.UUID

data class AfterSchoolStudent(
    val id: UUID? = null,
    val userId: UUID,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val name: String,
    val status1: Status,
    val status2: Status,
    val status3: Status
)
