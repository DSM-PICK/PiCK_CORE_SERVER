package dsm.pick2024.domain.user.port.out

interface ExistsUserPort {
    fun existsByAccountId(accountId: String): Boolean
}
