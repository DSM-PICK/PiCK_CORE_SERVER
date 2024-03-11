package dsm.pick2024.domain.selfstudy.presentation.dto.request

import javax.validation.constraints.NotBlank

data class TeacherRequest(
    @NotBlank
    val floor: Int,
    @NotBlank
    val teacher: String
)
