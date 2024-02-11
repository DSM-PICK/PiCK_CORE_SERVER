package dsm.pick2024.domain.application.presentation

import dsm.pick2024.domain.application.enums.ApplicationStatus
import dsm.pick2024.domain.application.port.`in`.*
import dsm.pick2024.domain.application.presentation.dto.request.ApplicationRequest
import dsm.pick2024.domain.application.presentation.dto.request.StatusApplicationRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@Tag(name = "application API")
@RestController
@RequestMapping("/application")
class ApplicationController(
    private val applicationUseCase: ApplicationUseCase,
    private val statusApplicationUseCase: StatusOKApplicationUseCase,
    private val statusApplicationChangeUseCase: StatusApplicationChangeUseCase,
    private val statusNOApplicationUseCase: StatusNOApplicationUseCase,
    private val queryFloorApplicationUseCase: QueryFloorApplicationUseCase,
    private val queryClassApplicationUseCase: QueryClassApplicationUseCase,
    private val queryReasonApplicationUseCase: QueryReasonApplicationUseCase
    private val queryFloorApplicationUseCase: QueryFloorApplicationUseCase,
    private val queryClassApplicationUseCase: QueryClassApplicationUseCase,
    private val queryReasonApplicationUseCase: QueryReasonApplicationUseCase,
    private val queryAllReasonApplicationUseCase: QueryAllReasonApplicationUseCase
) {

    @Operation(summary = "외출 신청 API")
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    fun application(
        @RequestBody applicationRequest: ApplicationRequest
    ) =
        applicationUseCase.application(applicationRequest)

    @Operation(summary = "외출신청 수락 API")
    @PatchMapping("/status/ok")
    fun statusOKApplication(
        @RequestBody statusApplicationRequest: StatusApplicationRequest?
    ) =
        statusApplicationUseCase.statusOKApplication(statusApplicationRequest!!)

    @Operation(summary = "외출신청 거절 API")
    @DeleteMapping("status/no")
    fun statusNOApplication(
        @RequestBody statusApplicationRequest: StatusApplicationRequest?
    ) =
        statusNOApplicationUseCase.statusNOApplication(statusApplicationRequest!!)

    @Operation(summary = "외출상태 복귀로 변경하기 API")
    @PatchMapping("/change/{applicationId}")
    fun statusApplicationChange(
        @PathVariable(name = "applicationId") applicationId: UUID,
        @RequestParam(name = "application_status") applicationStatus: ApplicationStatus
    ) =
        statusApplicationChangeUseCase.statusApplicationChange(applicationId, applicationStatus)

    @Operation(summary = "층별로 외출신청자 조회 API")
    @GetMapping("/floor")
    fun queryFloorApplication(
        @RequestParam(name = "floor") floor: Int
    ) =
        queryFloorApplicationUseCase.queryFloorApplication(floor)

    @Operation(summary = "반별로 외출신청자 조회 API")
    @GetMapping("/grade")
    fun queryClassApplication(
        @RequestParam(name = "grade") grade: Int,
        @RequestParam(name = "class_num") classNum: Int
    ) =
        queryClassApplicationUseCase.queryClassApplication(grade, classNum)

    @Operation(summary = "외출사유 확인하기 API")
    @GetMapping("/reason/{applicationId}")
    fun queryReasonApplication(
        @PathVariable(name = "applicationId") applicationId: UUID
    ) =
        queryReasonApplicationUseCase.queryReasonApplication(applicationId)

    @Operation(summary = "외출자 전체 사유 확인하기 API")
    @GetMapping("reason/all")
    fun queryAllReasonApplication() =
        queryAllReasonApplicationUseCase.queryAllReasonApplication()
}
