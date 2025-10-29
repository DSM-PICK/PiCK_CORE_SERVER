package dsm.pick2024.domain.user.persistence.repository

import dsm.pick2024.domain.user.entity.UserJpaEntity
import org.springframework.data.repository.Repository
import java.util.*

interface UserRepository : Repository<UserJpaEntity, UUID> {

    fun save(user: UserJpaEntity): UserJpaEntity

    fun findByAccountId(accountId: String): UserJpaEntity?

    fun findById(id: UUID): UserJpaEntity?

    fun findAllById(ids: Iterable<UUID>): List<UserJpaEntity>

    fun findAllByIdIn(ids: List<UUID>): List<UserJpaEntity>

    fun existsByAccountId(accountId: String): Boolean

    fun findByGradeAndClassNumAndNum(
        grade: Int,
        classNum: Int,
        num: Int
    ): UserJpaEntity?
}
