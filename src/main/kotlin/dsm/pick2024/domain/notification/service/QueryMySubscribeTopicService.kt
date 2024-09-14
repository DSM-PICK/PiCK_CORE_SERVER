package dsm.pick2024.domain.notification.service

import dsm.pick2024.domain.event.Topic
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
        val topicList = listOf(Topic.ALL, Topic.APPLICATION, Topic.CLASS_ROOM, Topic.NEW_NOTICE, Topic.WEEKEND_MEAL)

        return QueryMySubscribeTopicResponse(
            queryTopicSubscriptionPort.queryAllTopicSubscriptionByDeviceToken(user.deviceToken!!)
                !!.map { subscription ->
                    QuerySubscribeTopicResponse(
                        topic = subscription.topic,
                        isSubscribed = subscription.isSubscribed
                    )
                }
                .sortedBy { topicList.indexOf(it.topic) }
        )
    }
}
