package dsm.pick2024.domain.notification.port.out

import dsm.pick2024.domain.event.Topic

interface QueryNotificationPort {
    fun queryNotificationByDeviceTokenAndTopic(deviceToken: String, topic: Topic): List<Notification>?
}
