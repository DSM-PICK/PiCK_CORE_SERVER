package dsm.pick2024.domain.user.port.out

import dsm.pick2024.domain.user.domain.User
import java.util.UUID

interface QueryUserPort {
    fun findByAccountId(accountId: String): User?

    fun findByStudentNum(
        grade: Int,
        classNum: Int,
        num: Int
    ): User?

    fun userAll(): List<User>

    fun findById(userId: UUID): User?

    fun findByXquareId(xquareId: UUID): User?
}
