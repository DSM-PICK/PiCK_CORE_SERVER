package dsm.pick2024.domain.main

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/main")
class MainController(
    private val mainService: MainService
) {
    @GetMapping
    fun main() = mainService.main()
}
