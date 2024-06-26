package dsm.pick2024.domain.status.presentation

import dsm.pick2024.domain.status.port.`in`.ChangeStatusUseCase
import dsm.pick2024.domain.status.port.`in`.QueryClassStatusUseCase
import dsm.pick2024.domain.status.port.`in`.SaveAllStatusUserUseCase
import dsm.pick2024.domain.status.presentation.dto.request.ChangeStatusRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "status API")
@RestController
@RequestMapping("/status")
class StatusController(
    private val saveAllStatusUserUSeCase: SaveAllStatusUserUseCase,
    private val queryClassStatusUseCase: QueryClassStatusUseCase,
    private val changeStatusUseCase: ChangeStatusUseCase
) {
    @Operation(summary = "출석 관리 유저 정보 저장 API")
    @PostMapping("/saveAll")
    fun saveAll(
        @RequestParam key: String
    ) = saveAllStatusUserUSeCase.saveAll(key)

    @Operation(summary = "반별로 출석 상태 조회 API")
    @GetMapping("/grade")
    fun queryClassStatus(
        @RequestParam(name = "grade") grade: Int,
        @RequestParam(name = "class_num") classNum: Int
    ) = queryClassStatusUseCase.queryClasStatus(grade, classNum)

    @Operation(summary = "학생 출석 상태 변경 API")
    @PatchMapping("/change")
    fun changeStatus(
        @RequestBody request: List<ChangeStatusRequest>
    ) = changeStatusUseCase.changeStatus(request)
}
