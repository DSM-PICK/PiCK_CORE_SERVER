package dsm.pick2024.domain.notification.entity

import dsm.pick2024.domain.event.Topic
import dsm.pick2024.global.base.BaseUUIDEntity
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity(name = "tbl_topic_subscription")
class TopicSubscriptionJpaEntity(
    id: UUID? = null,
    @Enumerated(EnumType.STRING)
    val topic: Topic,
    @Column(columnDefinition = "TEXT")
    val deviceToken: String,
    val isSubscribed: Boolean = false,
    val userId: String
) : BaseUUIDEntity(id)
