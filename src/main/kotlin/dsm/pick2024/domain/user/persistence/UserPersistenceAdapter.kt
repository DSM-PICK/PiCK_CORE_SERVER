package dsm.pick2024.domain.user.persistence

import com.querydsl.jpa.impl.JPAQueryFactory
import dsm.pick2024.domain.user.domain.User
import dsm.pick2024.domain.user.entity.QUserJpaEntity
import dsm.pick2024.domain.user.exception.UserNotFoundException
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

    override fun findByUserId(userId: UUID): User? {
        val entity = userRepository.findById(userId).orElseThrow { UserNotFoundException }
        return userMapper.toDomain(entity)
    }

    override fun findByAccountId(accountId: String): User? =
        userRepository.findByAccountId(accountId)?.let { userMapper.toDomain(it) }

    override fun findByStudentNum(
        grade: Int,
        classNum: Int,
        num: Int
    ) = userRepository.findByGradeAndClassNumAndNum(grade, classNum, num)?.let { userMapper.toDomain(it) }

    override fun userAll() =
        jpaQueryFactory
            .selectFrom(QUserJpaEntity.userJpaEntity)
            .orderBy(
                QUserJpaEntity.userJpaEntity.grade.asc(),
                QUserJpaEntity.userJpaEntity.classNum.asc(),
                QUserJpaEntity.userJpaEntity.num.asc()
            )
            .fetch()
            .map { userMapper.toDomain(it) }

    override fun existsByAccountId(accountId: String) = userRepository.existsByAccountId(accountId)

    override fun save(user: User) {
        userRepository.save(userMapper.toEntity(user))
    }
}
