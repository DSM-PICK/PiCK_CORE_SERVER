package dsm.pick2024.domain.notification.persistence

import dsm.pick2024.domain.event.Topic
import dsm.pick2024.domain.notification.domain.Notification
import dsm.pick2024.domain.notification.mapper.NotificationMapper
import dsm.pick2024.domain.notification.persistence.repository.NotificationRepository
import dsm.pick2024.domain.notification.port.out.NotificationPort
import org.springframework.stereotype.Component

@Component
class NotificationPersistenceAdapter(
    private val notificationRepository: NotificationRepository,
    private val notificationMapper: NotificationMapper
): NotificationPort {
    override fun queryNotificationByDeviceTokenAndTopic(deviceToken: String, topic: Topic): List<Notification> {
        return notificationRepository.findByDeviceTokenAndTopic(deviceToken, topic).map { notificationMapper.toDomain(it) }
    }

    override fun save(notification: Notification) {
        notificationRepository.save(notificationMapper.toEntity(notification))
    }
}
