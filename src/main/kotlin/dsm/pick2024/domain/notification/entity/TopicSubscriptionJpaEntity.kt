package dsm.pick2024.domain.notification.entity

import dsm.pick2024.domain.event.Topic
import dsm.pick2024.global.base.BaseUUIDEntity
import java.util.*
import javax.persistence.Entity

@Entity
class TopicSubscriptionJpaEntity(
    id: UUID? = null,
    val topic: Topic,
    val deviceToken: String,
    val isSubscribed: Boolean = false
) : BaseUUIDEntity(id)
