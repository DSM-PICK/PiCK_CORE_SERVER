package dsm.pick2024.domain.event.sub

import dsm.pick2024.domain.event.ChangeStatusRequest
import dsm.pick2024.domain.event.UserInfoRequest
import dsm.pick2024.domain.main.port.`in`.MainUseCase
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class EventService(
    private val mainUseCase: MainUseCase,
) {

    @EventListener(UserInfoRequest::class)
    fun onChangeApplicationStatusEvent(event: ChangeStatusRequest) {
        event.userIdList.forEach {
            mainUseCase.onHandleEvent(it)
        }
    }

    @EventListener(UserInfoRequest::class)
    fun onCreateApplicationEvent(event: UserInfoRequest) {
        mainUseCase.onHandleEvent(event.userId)
    }

    @EventListener(UserInfoRequest::class)
    fun onReturnApplicationEvent(event: UserInfoRequest) {
        mainUseCase.onHandleEvent(event.userId)
    }

    @EventListener(UserInfoRequest::class)
    fun onBackUserClassroomEvent(event: UserInfoRequest) {
        mainUseCase.onHandleEvent(event.userId)
    }
}
