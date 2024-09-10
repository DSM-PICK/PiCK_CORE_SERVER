package dsm.pick2024.domain.event

import dsm.pick2024.domain.event.weekendmeal.SendMessageToWeekendMealEventPort
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/event")
class EventController(
    private val sendMessageToWeekendMealEventPort: SendMessageToWeekendMealEventPort
) {
    @GetMapping("/weekend-meal")
    fun sendToWeekendMeal() {
        sendMessageToWeekendMealEventPort.send()
    }
}
