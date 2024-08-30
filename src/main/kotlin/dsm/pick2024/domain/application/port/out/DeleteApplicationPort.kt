package dsm.pick2024.domain.application.port.out

import dsm.pick2024.domain.application.enums.ApplicationKind
import java.util.UUID

interface DeleteApplicationPort {
    fun deleteByIdAndApplicationKind(applicationId: UUID, applicationKind: ApplicationKind)

    fun deleteAll()

    fun deleteAllByApplicationKind(applicationKind: ApplicationKind)
}
