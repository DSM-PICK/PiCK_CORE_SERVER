package dsm.pick2024.domain.application.presentation

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.port.`in`.ApplicationUseCase
import dsm.pick2024.domain.application.port.`in`.QueryFloorApplicationUseCase
import dsm.pick2024.domain.application.port.`in`.StatusApplicationUseCase
import dsm.pick2024.domain.application.presentation.dto.request.ApplicationRequest
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
import java.util.UUID

@Tag(name = "application API")
@RestController
@RequestMapping("/application")
class ApplicationController(
    private val applicationUseCase: ApplicationUseCase,
    private val statusApplicationUseCase: StatusApplicationUseCase,
    private val queryFloorApplicationUseCase: QueryFloorApplicationUseCase
) {
    @Operation(summary = "외출 신청 API")
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    fun application(
        @RequestBody applicationRequest: ApplicationRequest
    ) = applicationUseCase.application(applicationRequest)

    @Operation(summary = "외출신청 수락, 거절 API")
    @PatchMapping("/status/{applicationId}")
    fun statusApplication(
        @RequestParam(name = "status") status: Status,
        @PathVariable(value = "applicationId") id: UUID
    ) =
        statusApplicationUseCase.statusApplication(status, id)

    @Operation(summary = "층별로 외출신청자 조회")
    @GetMapping("/floor")
    fun queryFloorApplication(@RequestParam floor: Int) =
        queryFloorApplicationUseCase.queryFloorApplication(floor)
}
