package dsm.pick2024.domain.application.port.out

import dsm.pick2024.domain.application.domain.Application
import dsm.pick2024.domain.application.enums.ApplicationKind
import dsm.pick2024.domain.application.enums.Status

interface QueryAllApplicationPort {
    fun findAllByApplicationKind(applicationKind: ApplicationKind): List<Application>?

    fun findAllByStatusAndApplicationKind(status: Status, applicationKind: ApplicationKind): List<Application>?
}
