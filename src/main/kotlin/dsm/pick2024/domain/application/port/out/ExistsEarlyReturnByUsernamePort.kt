package dsm.pick2024.domain.application.port.out

interface ExistsEarlyReturnByUsernamePort {
    fun existsByUsername(username: String): Boolean?
}
