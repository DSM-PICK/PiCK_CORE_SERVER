package dsm.pick2024.domain.afterschool.presentation

import dsm.pick2024.domain.afterschool.port.`in`.ChangeStatusAfterSchoolStudentUseCase
import dsm.pick2024.domain.afterschool.port.`in`.DeleteAfterSchoolStudentUseCase
import dsm.pick2024.domain.afterschool.port.`in`.QueryAllAfterSchoolStudentUseCase
import dsm.pick2024.domain.afterschool.port.`in`.QueryAllUserUseCase
import dsm.pick2024.domain.afterschool.port.`in`.SaveAfterSchoolStudentUseCase
import dsm.pick2024.domain.afterschool.presentation.dto.request.ChangeAfterSchoolStatusRequest
import dsm.pick2024.domain.afterschool.presentation.dto.request.DeleteRequest
import dsm.pick2024.domain.afterschool.presentation.dto.request.SaveAfterSchoolStudentRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@Tag(name = "after school API")
@RestController
@RequestMapping("/after")
class AfterSchoolStudentController(
    private val changeStatusAfterSchoolStudentUseCase: ChangeStatusAfterSchoolStudentUseCase,
    private val deleteAfterSchoolStudentUseCase: DeleteAfterSchoolStudentUseCase,
    private val queryAllAfterSchoolStudentUseCase: QueryAllAfterSchoolStudentUseCase,
    private val saveAfterSchoolStudentUseCase: SaveAfterSchoolStudentUseCase,
    private val queryAllUserUseCase: QueryAllUserUseCase
) {
    @Operation(summary = "학생등록 API")
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    fun saveAfterSchoolStudent(
        @RequestBody request: List<SaveAfterSchoolStudentRequest>
    ) = saveAfterSchoolStudentUseCase.saveAfterSchoolStudent(request)

    @Operation(summary = "학생 출결상태 변경")
    @PatchMapping("/change")
    fun changeAfterSchoolStudentStatus(
        @RequestBody changeAfterSchoolStatusRequest: List<ChangeAfterSchoolStatusRequest>
    ) = changeStatusAfterSchoolStudentUseCase.changeStatusAfterSchoolStudent(changeAfterSchoolStatusRequest)

    @Operation(summary = "학생제거 API")
    @DeleteMapping("/delete")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun deleteAfterSchoolStudent(
        @RequestBody request: DeleteRequest
    ) = deleteAfterSchoolStudentUseCase.deleteAfterSchoolStudent(request)

    @Operation(summary = "방과후 창조실 학생 조회 API")
    @GetMapping("/all")
    fun queryAfterSchoolAll() = queryAllAfterSchoolStudentUseCase.queryAfterSchoolStudentAll()

    @Operation(summary = "방과후 등록 자동완성 API")
    @GetMapping("/search")
    fun queryAllUser() = queryAllUserUseCase.queryAllUser()
}
