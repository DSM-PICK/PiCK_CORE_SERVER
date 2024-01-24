package dsm.pick2024_server.domain.user.persistence.repository

import dsm.pick2024_server.domain.user.entity.UserJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserRepository : JpaRepository<UserJpaEntity, UUID> {
    fun findByName(name: String): UserJpaEntity?
}
