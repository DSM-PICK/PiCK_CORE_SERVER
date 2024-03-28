package dsm.pick2024.domain.afterschool.presentation.dto.request

import javax.validation.constraints.Size

data class SaveAfterSchoolStudentRequest(
    @field:Size(min = 4, max = 4)
    val studentNum: String,
    val name: String
)
