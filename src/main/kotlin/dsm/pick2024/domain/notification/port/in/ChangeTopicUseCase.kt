package dsm.pick2024.domain.notification.port.`in`

import dsm.pick2024.domain.event.Topic

interface ChangeTopicUseCase {

    fun subscribeTopic(topic: Topic)

    fun unSubscribeTopic(topic: Topic)

}
