package dsm.pick2024.domain.status.port.out

import dsm.pick2024.domain.status.domain.Status
import java.util.UUID

interface FindStatusByUserIdPort {
    fun findStatusByUserId(id: UUID): Status?
}
