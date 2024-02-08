package dsm.pick2024.domain.application.port.out

interface ExistApplicationByUsernamePort {
    fun existsByUsername(username: String): Boolean?
}
