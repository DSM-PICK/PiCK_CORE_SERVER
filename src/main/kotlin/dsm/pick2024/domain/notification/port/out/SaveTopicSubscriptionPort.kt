package dsm.pick2024.domain.notification.port.out

interface SaveNotificationPort {
    fun save(notification: Notification)
}
