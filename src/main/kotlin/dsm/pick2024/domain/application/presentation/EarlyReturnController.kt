package dsm.pick2024.domain.application.presentation

import dsm.pick2024.domain.application.port.`in`.*
import dsm.pick2024.domain.application.presentation.dto.request.CreateEarlyReturnRequest
import dsm.pick2024.domain.application.presentation.dto.request.StatusEarlyReturnRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Tag(name = "early API")
@RestController
@RequestMapping("/early-return")
class EarlyReturnController(
    private val createEarlyReturnUseCase: CreateEarlyReturnUseCase,
    private val statusOKEarlyReturnUseCase: StatusOKEarlyReturnUseCase,
    private val statusNOEarlyReturnUseCase: StatusNOEarlyReturnUseCase,
    private val queryClassEarlyReturnUseCase: QueryClassEarlyReturnUseCase,
    private val queryFloorEarlyReturnUseCase: QueryFloorEarlyReturnUseCase,
    private val queryAllREarlyEarlyReturnUseCase: QueryAllREarlyEarlyReturnUseCase,
    private val queryMyEarlyReturnUseCase: QueryMyEarlyReturnUseCase
) {

    @Operation(summary = "조기귀가 신청 API")
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/create")
    fun createEarlyReturn(
        @RequestBody createEarlyReturnRequest: CreateEarlyReturnRequest
    ) =
        createEarlyReturnUseCase.createEarlyReturn(createEarlyReturnRequest)

    @Operation(summary = "조기귀가 신청 수락 API")
    @PatchMapping("/status/ok")
    fun statusNOEarlyReturn(
        @RequestBody statusEarlyReturnRequest: StatusEarlyReturnRequest?
    ) =
        statusOKEarlyReturnUseCase.statusOKEarlyReturn(statusEarlyReturnRequest!!)

    @Operation(summary = "조기귀가 신청 거절 API")
    @DeleteMapping("/status/no")
    fun statusEarlyReturn(
        @RequestBody statusEarlyReturnRequest: StatusEarlyReturnRequest?
    ) =
        statusNOEarlyReturnUseCase.statusNOEarlyReturn(statusEarlyReturnRequest!!)

    @Operation(summary = "반별로 조기귀가 신청자 조회 API")
    @GetMapping("/grade")
    fun queryClassEarlyReturn(
        @RequestParam(name = "grade") grade: Int,
        @RequestParam(name = "class_num") classNum: Int
    ) =
        queryClassEarlyReturnUseCase.queryClassApplication(grade, classNum)

    @Operation(summary = "층별로 조기귀가 신청자 조회 API")
    @GetMapping("/floor")
    fun queryFloorEarlyReturn(
        @RequestParam(name = "floor") floor: Int
    ) =
        queryFloorEarlyReturnUseCase.queryFloorApplication(floor)

    @Operation(summary = "조기귀가 신청자 사유 전체확인 API")
    @GetMapping("/reason/all")
    fun queryAllReasonEarlyReturn() =
        queryAllREarlyEarlyReturnUseCase.queryAllReasonEarlyReturn()

    @Operation(summary = "내 조기귀가증 확인 API")
    @GetMapping("/my")
    fun queryMyEarlyReturn() =
        queryMyEarlyReturnUseCase.queryMyEarlyReturn()
}
