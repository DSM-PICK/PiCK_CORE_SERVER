package dsm.pick2024.domain.outbox.domain

import dsm.pick2024.domain.outbox.enum.EventType
import dsm.pick2024.domain.outbox.presentation.dto.payload.PayloadInterFace
import dsm.pick2024.global.annotation.Aggregate
import java.util.*

@Aggregate
class Outbox(
    val id: UUID = UUID(0, 0),
    val payload: PayloadInterFace,
    val eventType: EventType
)
