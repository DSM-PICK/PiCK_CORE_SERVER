package dsm.pick2024.domain.afterschool.presentation

import dsm.pick2024.domain.afterschool.port.`in`.SaveAfterSchoolUseCase
import dsm.pick2024.domain.afterschool.presentation.dto.request.SaveAfterSchoolRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@Tag(name = "after school API")
@RestController
@RequestMapping("/after")
class AfterSchoolController(
    private val saveAfterSchoolUseCase: SaveAfterSchoolUseCase
) {

    @Operation(summary = "학생등록 API")
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    fun saveAfterSchool(saveAfterSchoolRequest: SaveAfterSchoolRequest) =
        saveAfterSchoolUseCase.saveAfterSchoolUseCase(saveAfterSchoolRequest)
}
