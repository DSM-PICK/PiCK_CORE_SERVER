package dsm.pick2024.domain.notification.mapper

import dsm.pick2024.domain.notification.domain.Notification
import dsm.pick2024.domain.notification.entity.NotificationJpaEntity
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.stereotype.Component

@Component
class NotificationMapper {
    fun toEntity(domain: Notification) =
        domain.run {
            NotificationJpaEntity(
                id = id,
                deviceToken = deviceToken,
                isSubscribed = isSubscribed,
                topic = topic
            )
        }

    fun toDomain(entity: NotificationJpaEntity) =
        entity.run {
            Notification(
                id = id,
                deviceToken = deviceToken,
                isSubscribed = isSubscribed,
                topic = topic
            )
        }
}
