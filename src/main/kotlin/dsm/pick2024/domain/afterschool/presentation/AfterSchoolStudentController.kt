package dsm.pick2024.domain.afterschool.presentation

import dsm.pick2024.domain.afterschool.port.`in`.ChangeStatusAfterSchoolStudentUseCase
import dsm.pick2024.domain.afterschool.port.`in`.DeleteAfterSchoolStudentUseCase
import dsm.pick2024.domain.afterschool.port.`in`.QueryAfterSchoolStudentAllUseCase
import dsm.pick2024.domain.afterschool.port.`in`.SaveAfterSchoolStudentUseCase
import dsm.pick2024.domain.afterschool.presentation.dto.request.ChangeAfterSchoolStatusRequest
import dsm.pick2024.domain.afterschool.presentation.dto.request.SaveAfterSchoolStudentRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.UUID

@Tag(name = "after school API")
@RestController
@RequestMapping("/after")
class AfterSchoolStudentController(
    private val saveAfterSchoolUseCase: SaveAfterSchoolStudentUseCase,
    private val changeStatusAfterSchoolStudentUseCase: ChangeStatusAfterSchoolStudentUseCase,
    private val deleteAfterSchoolStudentUseCase: DeleteAfterSchoolStudentUseCase,
    private val queryAfterSchoolStudentAllUseCase: QueryAfterSchoolStudentAllUseCase
) {

    @Operation(summary = "학생등록 API")
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    fun saveAfterSchoolStudent(
        @RequestBody saveAfterSchoolStudentRequest: List<SaveAfterSchoolStudentRequest>
    ) =
        saveAfterSchoolUseCase.saveAfterSchoolStudentUseCase(saveAfterSchoolStudentRequest)

    @Operation(summary = "학생 출결상태 변경")
    @PatchMapping("/change")
    fun changeAfterSchoolStudentStatus(
        @RequestBody changeAfterSchoolStatusRequest: List<ChangeAfterSchoolStatusRequest>
    ) =
        changeStatusAfterSchoolStudentUseCase.changeStatusAfterSchoolStudent(changeAfterSchoolStatusRequest)

    @Operation(summary = "학생제거 API")
    @DeleteMapping("/delete")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun deleteAfterSchoolStudent(@RequestBody id: UUID) =
        deleteAfterSchoolStudentUseCase.deleteAfterSchoolStudent(id)

    @Operation(summary = "방과후 학생 전체 조회 API")
    @GetMapping("/all")
    fun queryAfterSchoolAll() =
        queryAfterSchoolStudentAllUseCase.queryAfterSchoolStudentAll()
}
