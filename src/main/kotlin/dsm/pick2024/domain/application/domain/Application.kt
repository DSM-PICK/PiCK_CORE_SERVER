package dsm.pick2024.domain.application.domain

import dsm.pick2024.domain.user.entity.UserJpaEntity
import java.time.LocalTime
import java.util.UUID

data class Application(
    val id: UUID? = null,
    val reason: String,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val username: String
)
