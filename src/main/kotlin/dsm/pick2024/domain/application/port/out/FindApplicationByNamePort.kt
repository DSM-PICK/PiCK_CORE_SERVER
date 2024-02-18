package dsm.pick2024.domain.application.port.out

import dsm.pick2024.domain.application.domain.Application

interface FindApplicationByNamePort {
    fun findByUsername(username: String): Application?
}
