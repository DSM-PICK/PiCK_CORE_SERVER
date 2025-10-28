package dsm.pick2024.domain.user.finder

import dsm.pick2024.domain.user.exception.UserNotFoundException
import dsm.pick2024.domain.user.port.`in`.UserFinderUseCase
import dsm.pick2024.domain.user.port.out.QueryUserPort
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserFinder(
    private val queryUserPort: QueryUserPort
) : UserFinderUseCase {
    override fun findByAccountIdOrThrow(accountId: String) =
        queryUserPort.findByAccountId(accountId) ?: throw UserNotFoundException

    override fun findByStudentNumOrThrow(grade: Int, classNum: Int, num: Int) =
        queryUserPort.findByStudentNum(grade, classNum, num) ?: throw UserNotFoundException

    override fun userAllOrThrow() =
        queryUserPort.userAll() ?: throw UserNotFoundException

    override fun findByUserIdOrThrow(userId: UUID) =
        queryUserPort.findByUserId(userId) ?: throw UserNotFoundException
}
