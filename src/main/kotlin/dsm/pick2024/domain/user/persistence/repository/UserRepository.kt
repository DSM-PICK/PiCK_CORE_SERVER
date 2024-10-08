package dsm.pick2024.domain.user.persistence.repository

import dsm.pick2024.domain.user.entity.UserJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserRepository : JpaRepository<UserJpaEntity, UUID> {

    fun findByAccountId(accountId: String): UserJpaEntity?

    fun findByXquareId(xquareId: UUID): UserJpaEntity?

    fun existsByAccountId(accountId: String): Boolean

    fun findByGradeAndClassNumAndNum(
        grade: Int,
        classNum: Int,
        num: Int
    ): UserJpaEntity?
}
