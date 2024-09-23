package dsm.pick2024.domain.event.main

import dsm.pick2024.domain.application.event.ChangeApplicationStatusEvent
import dsm.pick2024.domain.application.event.CreateApplicationEvent
import dsm.pick2024.domain.application.event.ReturnApplicationEvent
import dsm.pick2024.domain.classroom.event.BackUserClassroomEvent
import dsm.pick2024.domain.main.MainService
import dsm.pick2024.domain.main.port.`in`.MainUseCase
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class MainEventService(
    private val mainUseCase: MainUseCase
) {

    @EventListener(ChangeApplicationStatusEvent::class)
    fun onChangeApplicationStatusEvent(event: ChangeApplicationStatusEvent) {
        event.userIdList.forEach {
            mainUseCase.onHandleEvent(it)
        }
    }

    @EventListener(CreateApplicationEvent::class)
    fun onCreateApplicationEvent(event: CreateApplicationEvent) {
        mainUseCase.onHandleEvent(event.userId)
    }

    @EventListener(ReturnApplicationEvent::class)
    fun onReturnApplicationEvent(event: ReturnApplicationEvent) {
        mainUseCase.onHandleEvent(event.userId)
    }

    @EventListener(BackUserClassroomEvent::class)
    fun onBackUserClassroomEvent(event: BackUserClassroomEvent) {
        mainUseCase.onHandleEvent(event.userId)
    }
}
