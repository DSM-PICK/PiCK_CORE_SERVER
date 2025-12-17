package dsm.pick2024.domain.earlyreturn.presentation

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.port.`in`.QueryFloorAndStatusApplicationUseCase
import dsm.pick2024.domain.application.presentation.dto.response.QueryApplicationResponse
import dsm.pick2024.domain.earlyreturn.port.`in`.CreateEarlyReturnUseCase
import dsm.pick2024.domain.earlyreturn.port.`in`.QueryAllOKEarlyReturnUseCase
import dsm.pick2024.domain.earlyreturn.port.`in`.QueryAllREarlyEarlyReturnUseCase
import dsm.pick2024.domain.earlyreturn.port.`in`.QueryClassEarlyReturnUseCase
import dsm.pick2024.domain.earlyreturn.port.`in`.QueryMyEarlyReturnUseCase
import dsm.pick2024.domain.earlyreturn.port.`in`.ChangeEarlyReturnStatusUseCase
import dsm.pick2024.domain.earlyreturn.port.`in`.QueryFloorAndStatusEarlyReturnUseCase
import dsm.pick2024.domain.earlyreturn.port.`in`.QueryFloorOKEarlyReturnUseCase
import dsm.pick2024.domain.earlyreturn.presentation.dto.request.CreateEarlyReturnRequest
import dsm.pick2024.domain.earlyreturn.presentation.dto.request.StatusEarlyReturnRequest
import dsm.pick2024.domain.earlyreturn.presentation.dto.response.QueryEarlyReturnResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@Tag(name = "early API")
@RestController
@RequestMapping("/early-return")
class EarlyReturnController(
    private val createEarlyReturnUseCase: CreateEarlyReturnUseCase,
    private val changeEarlyReturnStatusUseCase: ChangeEarlyReturnStatusUseCase,
    private val queryClassEarlyReturnUseCase: QueryClassEarlyReturnUseCase,
    private val queryAllREarlyEarlyReturnUseCase: QueryAllREarlyEarlyReturnUseCase,
    private val queryMyEarlyReturnUseCase: QueryMyEarlyReturnUseCase,
    private val queryAllOKEarlyReturnUseCase: QueryAllOKEarlyReturnUseCase,
    private val queryFloorOKEarlyReturnUseCase: QueryFloorOKEarlyReturnUseCase,
    private val queryFloorAndStatusEarlyReturnUseCase: QueryFloorAndStatusEarlyReturnUseCase
) {
    @Operation(summary = "조기귀가 신청 API")
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/create")
    fun createEarlyReturn(
        @Valid @RequestBody
        createEarlyReturnRequest: CreateEarlyReturnRequest
    ) = createEarlyReturnUseCase.createEarlyReturn(createEarlyReturnRequest)

    @Operation(summary = "조기귀가 신청 수락또는 거절 API")
    @PatchMapping("/status")
    fun statusNOEarlyReturn(
        @RequestBody statusEarlyReturnRequest: StatusEarlyReturnRequest
    ) = changeEarlyReturnStatusUseCase.statusEarlyReturn(statusEarlyReturnRequest)

    @Operation(summary = "반별로 조기귀가 신청자 조회 API")
    @GetMapping("/grade")
    fun queryClassEarlyReturn(
        @RequestParam(name = "grade") grade: Int,
        @RequestParam(name = "class_num") classNum: Int
    ) = queryClassEarlyReturnUseCase.queryClassApplication(grade, classNum)

    @Operation(summary = "층별로 조기귀가자/신청자 조회 API")
    @GetMapping("/floor")
    fun queryFloorEarlyReturn(
        @RequestParam(name = "floor") floor: Int,
        @RequestParam(name = "status") status: Status
    ): List<QueryEarlyReturnResponse> {

        return queryFloorAndStatusEarlyReturnUseCase
            .queryFloorAndStatusEarlyReturn(floor, status)
    }


    @Operation(summary = "조기귀가 확인된 사람 사유 전체확인 API")
    @GetMapping("/reason/ok-all")
    fun queryAllReasonEarlyReturn() = queryAllREarlyEarlyReturnUseCase.queryAllReasonEarlyReturn()

    @Operation(summary = "내 조기귀가증 확인 API")
    @GetMapping("/my")
    fun queryMyEarlyReturn() = queryMyEarlyReturnUseCase.queryMyEarlyReturn()

    @Operation(summary = "조기귀가중인 학생 조회 API")
    @GetMapping("/ok")
    fun queryOKEarlyReturn() = queryAllOKEarlyReturnUseCase.queryAllOKEarlyReturn()
}
