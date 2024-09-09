package dsm.pick2024.domain.notification.service

import dsm.pick2024.domain.notification.port.`in`.QueryMySubscribeTopicUseCase
import dsm.pick2024.domain.notification.port.out.QueryTopicSubscriptionPort
import dsm.pick2024.domain.notification.presentation.dto.response.QueryMySubscribeTopicResponse
import dsm.pick2024.domain.notification.presentation.dto.response.QuerySubscribeTopicResponse
import dsm.pick2024.domain.user.facade.UserFacade
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryMySubscribeTopicService(
    private val userFacade: UserFacade,
    private val queryTopicSubscriptionPort: QueryTopicSubscriptionPort
) : QueryMySubscribeTopicUseCase {

    @Transactional(readOnly = true)
    override fun execute(): QueryMySubscribeTopicResponse {
        val user = userFacade.currentUser()
        return QueryMySubscribeTopicResponse(
            queryTopicSubscriptionPort.queryAllNotificationByDeviceToken(user.deviceToken!!)
                .map { subscription ->
                    QuerySubscribeTopicResponse(
                        topic = subscription.topic,
                        isSubscribed = subscription.isSubscribed
                    )
                }
        )
    }
}
