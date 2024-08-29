package dsm.pick2024.domain.application.presentation

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.port.`in`.ApplicationUseCase
import dsm.pick2024.domain.application.port.`in`.QueryAllReasonApplicationUseCase
import dsm.pick2024.domain.application.port.`in`.QueryClassApplicationUseCase
import dsm.pick2024.domain.application.port.`in`.QueryFloorAndStatusApplicationUseCase
import dsm.pick2024.domain.application.port.`in`.QueryMyApplicationUseCase
import dsm.pick2024.domain.application.port.`in`.QueryStatusApplicationUseCase
import dsm.pick2024.domain.application.port.`in`.ReturnApplicationStatusUseCase
import dsm.pick2024.domain.application.port.`in`.ChangeApplicationStatusUseCase
import dsm.pick2024.domain.application.presentation.dto.request.ApplicationRequest
import dsm.pick2024.domain.application.presentation.dto.request.ApplicationStatusRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID
import javax.validation.Valid

@Tag(name = "application API")
@RestController
@RequestMapping("/application")
class ApplicationController(
    private val applicationUseCase: ApplicationUseCase,
    private val changeApplicationStatusUseCase: ChangeApplicationStatusUseCase,
    private val returnApplicationStatusUseCase: ReturnApplicationStatusUseCase,
    private val queryFloorApplicationUseCase: QueryFloorAndStatusApplicationUseCase,
    private val queryClassApplicationUseCase: QueryClassApplicationUseCase,
    private val queryAllReasonApplicationUseCase: QueryAllReasonApplicationUseCase,
    private val queryMyApplicationUseCase: QueryMyApplicationUseCase,
    private val queryStatusApplicationUseCase: QueryStatusApplicationUseCase
) {
    @Operation(summary = "외출 신청 API")
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    fun application(
        @Valid @RequestBody
        applicationRequest: ApplicationRequest
    ) = applicationUseCase.application(applicationRequest)

    @Operation(summary = "외출신청 수락또는 거절 API")
    @PatchMapping("/status")
    fun statusOKApplication(
        @RequestBody applicationStatusRequest: ApplicationStatusRequest
    ) = changeApplicationStatusUseCase.changeStatusApplication(applicationStatusRequest)

    @Operation(summary = "외출상태 복귀로 변경하기 API")
    @PatchMapping("/return")
    fun statusApplicationChange(
        @RequestBody applicationId: List<UUID>
    ) = returnApplicationStatusUseCase.returnApplicationStatus(applicationId)

    @Operation(summary = "층별로 외출자/신청자 조회 API")
    @GetMapping("/floor")
    fun queryFloorApplication(
        @RequestParam(name = "floor") floor: Int,
        @RequestParam(name = "status") status: Status
    ) = queryFloorApplicationUseCase.queryFloorAndStatusApplication(floor, status)

    @Operation(summary = "반별로 외출신청자 조회 API")
    @GetMapping("/grade")
    fun queryClassApplication(
        @RequestParam(name = "grade") grade: Int,
        @RequestParam(name = "class_num") classNum: Int
    ) = queryClassApplicationUseCase.queryClassApplication(grade, classNum)

    @Operation(summary = "외출자 전체 사유 확인하기 API")
    @GetMapping("/reason/all")
    fun queryAllReasonApplication() = queryAllReasonApplicationUseCase.queryAllReasonApplication()

    @Operation(summary = "내 외출증 조회 API")
    @GetMapping("/my")
    fun queryMyApplication() = queryMyApplicationUseCase.queryMyApplication()

    @Operation(summary = "학생 조회 API")
    @GetMapping("/status")
    fun queryStatusApplication() = queryStatusApplicationUseCase.queryStatusApplication()
}
