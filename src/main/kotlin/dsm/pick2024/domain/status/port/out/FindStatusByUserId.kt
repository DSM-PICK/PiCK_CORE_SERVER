package dsm.pick2024.domain.status.port.out

import dsm.pick2024.domain.status.domain.Status
import java.util.UUID

interface FindStatusByUserId {
    fun findStatusByUserId(id: UUID): Status?
}
