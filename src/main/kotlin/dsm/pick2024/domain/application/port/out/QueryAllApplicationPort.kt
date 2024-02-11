package dsm.pick2024.domain.application.port.out

import dsm.pick2024.domain.application.domain.Application

interface QueryAllApplicationPort {
    fun findAll(): List<Application>
}
