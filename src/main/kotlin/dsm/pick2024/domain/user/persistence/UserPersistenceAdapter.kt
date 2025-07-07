package dsm.pick2024.domain.user.persistence

import com.querydsl.jpa.impl.JPAQueryFactory
import dsm.pick2024.domain.user.domain.User
import dsm.pick2024.domain.user.entity.QUserJpaEntity
import dsm.pick2024.domain.user.mapper.UserMapper
import dsm.pick2024.domain.user.persistence.repository.UserRepository
import dsm.pick2024.domain.user.port.out.UserPort
import java.util.UUID
import org.springframework.stereotype.Component

@Component
class UserPersistenceAdapter(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
    private val jpaQueryFactory: JPAQueryFactory
) : UserPort {

    override fun findByUserId(userId: UUID): User? = userRepository.findById(userId)?.let { userMapper.toDomain(it) }

    fun existByAccountId(accountId: String): Boolean {
        return userRepository.existsByAccountId(accountId)
    }

    override fun findByStudentNum(
        grade: Int,
        classNum: Int,
        num: Int
    ) = userRepository.findByGradeAndClassNumAndNum(grade, classNum, num)?.let { userMapper.toDomain(it) }

    override fun userAll() =
        jpaQueryFactory
            .selectFrom(QUserJpaEntity.userJpaEntity)
            .fetch()
            .map { userMapper.toDomain(it) }

    override fun existsByAccountId(accountId: String) = userRepository.existsByAccountId(accountId)

    override fun save(user: User): User {
        val entity = userMapper.toEntity(user)
        val saved = userRepository.save(entity)
        return userMapper.toDomain(saved)
    }

    override fun getUserById(id: UUID): User? {
        return userRepository.findById(id)?.let { userMapper.toDomain(it) }
    }

    override fun findByAccountId(accountId: String): User? {
        return userRepository.findByAccountId(accountId)?.let { userMapper.toDomain(it) }
    }

    override fun updateUserPassword(userId: UUID, password: String) {
        jpaQueryFactory
            .update(QUserJpaEntity.userJpaEntity)
            .set(QUserJpaEntity.userJpaEntity.password, password)
            .where(QUserJpaEntity.userJpaEntity.id.eq(userId))
            .execute()
    }
}
