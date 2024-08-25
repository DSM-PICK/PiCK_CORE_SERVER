package dsm.pick2024.domain.application.port.out

import dsm.pick2024.domain.application.domain.Application
import dsm.pick2024.domain.application.enums.ApplicationKind

interface SaveApplicationPort {
    fun save(application: Application)

    fun saveAll(application: List<Application>)
}
