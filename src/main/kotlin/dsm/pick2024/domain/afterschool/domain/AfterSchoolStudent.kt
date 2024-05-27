package dsm.pick2024.domain.afterschool.domain

import dsm.pick2024.domain.afterschool.enums.Status
import dsm.pick2024.global.annotation.Aggregate
import java.util.UUID

@Aggregate
data class AfterSchoolStudent(
    val id: UUID? = null,
    val userId: UUID,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val userName: String,
    val status1: Status,
    val status2: Status,
    val status3: Status
)
