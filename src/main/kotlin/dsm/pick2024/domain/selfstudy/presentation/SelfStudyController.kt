package dsm.pick2024.domain.selfstudy.presentation

import dsm.pick2024.domain.selfstudy.port.`in`.RegistrationSelfStudyTeacherUseCase
import dsm.pick2024.domain.selfstudy.presentation.dto.request.RegistrationSelfStudyTeacherRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@Tag(name = "selfStudy API")
@RestController
@RequestMapping("/self-study")
class SelfStudyController(
    private val registrationSelfStudyTeacherUseCase: RegistrationSelfStudyTeacherUseCase
) {

    @Operation(summary = "자습감독 선생님 등록 API")
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/register")
    fun selfStudyTeacherRegister(registrationSelfStudyTeacherRequest: RegistrationSelfStudyTeacherRequest) =
        registrationSelfStudyTeacherUseCase.registrationSelfStudyTeacher(registrationSelfStudyTeacherRequest)
}
