package dsm.pick2024_server.domain.user.mapper

import dsm.pick2024_server.domain.user.domain.User
import dsm.pick2024_server.domain.user.entity.UserJpaEntity
import org.springframework.stereotype.Component

@Component
class UserMapper {

    fun toEntity(domain: User): UserJpaEntity =
        domain.run {
            UserJpaEntity(
                id = id,
                name = name
            )
        }

    fun toDomain(entity: UserJpaEntity): User =
        entity.run {
            User(
                id = id!!,
                name = name
            )
        }
}
