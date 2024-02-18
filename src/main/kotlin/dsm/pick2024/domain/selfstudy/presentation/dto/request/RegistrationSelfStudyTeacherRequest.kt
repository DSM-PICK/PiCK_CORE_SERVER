package dsm.pick2024.domain.selfstudy.presentation.dto.request

import java.time.LocalDate
import javax.validation.constraints.NotBlank

data class RegistrationSelfStudyTeacherRequest(
    @NotBlank
    val floor: Int,
    @NotBlank
    val teacher: String,
    @NotBlank
    val date: LocalDate
)
