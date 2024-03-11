package dsm.pick2024.domain.user.port.out

interface ExistsByAccountIdPort {
    fun existsByAccountId(accountId: String): Boolean
}
