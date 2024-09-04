package dsm.pick2024.domain.notification.entity

import dsm.pick2024.domain.event.Topic
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class NotificationJpaEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val topic: Topic,
    val deviceToken: String,
    val isSubscribed: Boolean = false
)
