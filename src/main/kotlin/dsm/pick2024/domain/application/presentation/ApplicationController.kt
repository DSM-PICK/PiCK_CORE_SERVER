package dsm.pick2024.domain.application.presentation

import dsm.pick2024.domain.application.presentation.dto.request.ApplicationRequest
import dsm.pick2024.domain.application.service.ApplicationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@Tag(name = "application API")
@RestController
@RequestMapping("/application")
class ApplicationController(
    private val applicationService: ApplicationService
) {
    @Operation(summary = "외출 신청 API")
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping()
    fun application(
        @RequestBody applicationRequest: ApplicationRequest
    ) = applicationService.application(applicationRequest)
}
