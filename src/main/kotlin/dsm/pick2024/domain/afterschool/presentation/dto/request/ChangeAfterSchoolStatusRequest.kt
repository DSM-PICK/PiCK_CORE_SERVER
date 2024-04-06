package dsm.pick2024.domain.afterschool.presentation.dto.request

import dsm.pick2024.domain.afterschool.enums.Status
import java.util.UUID

data class ChangeAfterSchoolStatusRequest(
    val list: List<Change>
)

data class Change(
    val period: Int,
    val afterSchool: List<AfterSchool>
)

data class AfterSchool(
    val userId: UUID,
    val status: Status
)
