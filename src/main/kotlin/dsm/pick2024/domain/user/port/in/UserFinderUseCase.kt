package dsm.pick2024.domain.user.port.`in`

import dsm.pick2024.domain.user.domain.User
import java.util.*

interface UserFinderUseCase {
    fun findByAccountIdOrThrow(accountId: String): User

    fun findByStudentNumOrThrow(
        grade: Int,
        classNum: Int,
        num: Int
    ): User

    fun findByUserIdOrThrow(userId: UUID): User
}
