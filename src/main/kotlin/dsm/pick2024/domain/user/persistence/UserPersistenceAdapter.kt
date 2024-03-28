package dsm.pick2024.domain.user.persistence

import dsm.pick2024.domain.user.domain.User
import dsm.pick2024.domain.user.mapper.UserMapper
import dsm.pick2024.domain.user.persistence.repository.UserRepository
import dsm.pick2024.domain.user.port.out.UserStudentNumPort
import org.springframework.stereotype.Component

@Component
class UserPersistenceAdapter(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper
) : UserStudentNumPort {
    override fun findByAccountId(accountId: String): User? =
        userRepository.findByAccountId(accountId)?.let { userMapper.toDomain(it) }

    override fun findByStudentNum(
        grade: Int,
        classNum: Int,
        num: Int
    ) = userRepository.findByGradeAndClassNumAndNum(grade, classNum, num)?.let { userMapper.toDomain(it) }

    override fun userAll() = userRepository.findAll().map { userMapper.toDomain(it) }

    override fun existsByAccountId(accountId: String) = userRepository.existsByAccountId(accountId)

    override fun save(user: User) {
        userRepository.save(userMapper.toEntity(user))
    }
}
