package dsm.pick2024.domain.outbox.port.`in`

interface OutboxFacadeUseCase {
    fun sendNotificationAll(deviceToken: List<String?>, title: String, body: String)
    fun sendNotification(deviceToken: String, title: String, body: String)
}
