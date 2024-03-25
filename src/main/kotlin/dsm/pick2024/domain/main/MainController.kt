package dsm.pick2024.domain.main

import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/main")
class MainController(
    private val mainService: MainService
) {
    @Operation(summary = "main")
    @GetMapping
    fun main() = mainService.main()
}
