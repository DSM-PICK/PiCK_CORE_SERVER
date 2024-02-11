package dsm.pick2024.domain.application.port.out

import dsm.pick2024.domain.application.domain.Application

interface QueryAllReasonApplicationPort {
    fun findAll(): List<Application?>
}
