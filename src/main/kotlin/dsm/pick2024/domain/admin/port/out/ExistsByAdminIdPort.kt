package dsm.pick2024.domain.admin.port.out

interface ExistsByAdminIdPort {
    fun existsByAdminId(adminId: String): Boolean

    fun existByName(name: String): Boolean
}
