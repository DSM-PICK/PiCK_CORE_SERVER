package dsm.pick2024.domain.notification.port.out

import dsm.pick2024.domain.notification.domain.Notification

interface SaveNotificationPort {
    fun save(notification: Notification)
}
