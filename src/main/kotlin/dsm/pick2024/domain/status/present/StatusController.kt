package dsm.pick2024.domain.status.present

import dsm.pick2024.domain.status.port.`in`.SaveAllStatusUserUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "status API")
@RestController
@RequestMapping("/status")
class StatusController(
    private val saveAllStatusUserUSeCase: SaveAllStatusUserUseCase
) {
    @Operation(summary = "주말급식 유저 정보 저장 API")
    @PostMapping("/saveAll")
    fun saveAll(@RequestParam key: String) = saveAllStatusUserUSeCase.saveAll(key)
}
