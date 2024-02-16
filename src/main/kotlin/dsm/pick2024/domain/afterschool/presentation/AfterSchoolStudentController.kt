package dsm.pick2024.domain.afterschool.presentation

import dsm.pick2024.domain.afterschool.port.`in`.SaveAfterSchoolStudentUseCase
import dsm.pick2024.domain.afterschool.presentation.dto.request.SaveAfterSchoolStudentRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Tag(name = "after school API")
@RestController
@RequestMapping("/after")
class AfterSchoolStudentController(
    private val saveAfterSchoolUseCase: SaveAfterSchoolStudentUseCase
) {

    @Operation(summary = "학생등록 API")
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    fun saveAfterSchool(@RequestBody saveAfterSchoolStudentRequest: SaveAfterSchoolStudentRequest) =
        saveAfterSchoolUseCase.saveAfterSchoolStudentUseCase(saveAfterSchoolStudentRequest)
}
