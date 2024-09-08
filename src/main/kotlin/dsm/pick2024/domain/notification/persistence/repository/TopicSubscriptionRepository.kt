package dsm.pick2024.domain.notification.persistence.repository

import dsm.pick2024.domain.notification.entity.TopicSubscriptionJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface TopicSubscriptionRepository : JpaRepository<TopicSubscriptionJpaEntity, UUID>
