package dsm.pick2024.domain.application.port.out

import dsm.pick2024.domain.application.domain.Application

interface DeleteAllApplicationPort {
    fun deleteAll(application: List<Application>)
}
