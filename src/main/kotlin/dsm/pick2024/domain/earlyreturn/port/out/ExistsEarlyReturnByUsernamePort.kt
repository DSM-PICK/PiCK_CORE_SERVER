package dsm.pick2024.domain.earlyreturn.port.out

interface ExistsEarlyReturnByUsernamePort {
    fun existsByUsername(username: String): Boolean?
}
