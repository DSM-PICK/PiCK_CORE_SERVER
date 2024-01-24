package dsm.pick2024_server.domain.user.port.out

import dsm.pick2024_server.domain.user.domain.User


interface UserByNamePort {
    fun findByName(name: String): User?
}
