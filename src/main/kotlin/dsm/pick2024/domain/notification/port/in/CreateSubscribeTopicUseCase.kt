package dsm.pick2024.domain.notification.port.`in`

interface CreateSubscribeTopicUseCase {
    fun execute(deviceToken: String)
}
