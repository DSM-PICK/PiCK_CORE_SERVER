package dsm.pick2024.domain.notification.persistence.repository

import dsm.pick2024.domain.event.Topic
import dsm.pick2024.domain.notification.entity.NotificationJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface NotificationRepository : JpaRepository<NotificationJpaEntity, Long> {
    fun findByDeviceTokenAndTopic(deviceToken: String, topic: Topic): List<NotificationJpaEntity>
}
