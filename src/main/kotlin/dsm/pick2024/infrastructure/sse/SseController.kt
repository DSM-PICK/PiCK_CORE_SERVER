package dsm.pick2024.infrastructure.sse

import dsm.pick2024.domain.main.port.`in`.MainUseCase
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@RestController
class SseController(
    private val userFacadeUseCase: UserFacadeUseCase,
    private val sseRegistry: SseRegistry,
    private val mainUseCase: MainUseCase
) {

    @GetMapping("/event")
    fun subscribe(): SseEmitter {
        val user = userFacadeUseCase.currentUser()

        val emitter = sseRegistry.add(user.id)
        mainUseCase.sendEvent(user.id)

        return emitter
    }
}
