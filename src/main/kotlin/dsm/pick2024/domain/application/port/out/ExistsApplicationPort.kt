package dsm.pick2024.domain.application.port.out

import dsm.pick2024.domain.application.enums.ApplicationKind
import dsm.pick2024.domain.application.enums.Status
import java.util.*

interface ExistsApplicationPort {

    fun existByUserId(userId: UUID): Boolean

    fun existsByUserIdAndApplicationKind
    (userId: UUID, applicationKind: ApplicationKind): Boolean

    fun existsByStatusAndUserIdAndApplicationKind
    (status: Status, userId: UUID, applicationKind: ApplicationKind): Boolean
}
