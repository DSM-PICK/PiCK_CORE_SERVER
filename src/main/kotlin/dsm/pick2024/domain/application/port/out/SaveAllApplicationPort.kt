package dsm.pick2024.domain.application.port.out

import dsm.pick2024.domain.application.domain.Application

interface SaveAllApplicationPort {
    fun saveAll(application: List<Application>)
}
