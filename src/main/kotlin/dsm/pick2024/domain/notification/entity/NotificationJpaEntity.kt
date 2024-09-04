package dsm.pick2024.domain.notification.entity

import dsm.pick2024.domain.event.Topic
import dsm.pick2024.global.base.BaseUUIDEntity
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class NotificationJpaEntity (
    id: UUID?,
    val topic: Topic,
    val deviceToken: String,
    val isSubscribed: Boolean = false
): BaseUUIDEntity(id)
