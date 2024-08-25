package dsm.pick2024.domain.application.port.out

import dsm.pick2024.domain.application.enums.ApplicationKind
import java.util.*

interface ExistsApplicationPort {

    fun existsByUserId(userId: UUID, applicationKind: ApplicationKind): Boolean

    fun existsOKByUserId(userId: UUID, applicationKind: ApplicationKind): Boolean
}
