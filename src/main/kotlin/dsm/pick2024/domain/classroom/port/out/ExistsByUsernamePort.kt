package dsm.pick2024.domain.classroom.port.out

interface ExistsByUsernamePort {
    fun existsByUsername(username: String): Boolean?
}
