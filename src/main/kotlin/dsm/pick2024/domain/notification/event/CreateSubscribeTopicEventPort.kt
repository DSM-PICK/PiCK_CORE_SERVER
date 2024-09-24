package dsm.pick2024.domain.notification.event

interface CreateSubscribeTopicEventPort {
    fun execute(deviceToken: String, accountId: String)
}
