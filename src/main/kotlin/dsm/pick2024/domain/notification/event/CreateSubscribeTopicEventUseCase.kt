package dsm.pick2024.domain.notification.event

interface CreateSubscribeTopicEventUseCase {
    fun execute(deviceToken: String)
}
