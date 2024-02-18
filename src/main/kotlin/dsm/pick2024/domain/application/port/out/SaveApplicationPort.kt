package dsm.pick2024.domain.application.port.out

import dsm.pick2024.domain.application.domain.Application

interface SaveApplicationPort {
    fun save(application: Application)
}
