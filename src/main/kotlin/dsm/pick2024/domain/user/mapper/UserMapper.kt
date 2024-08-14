package dsm.pick2024.domain.user.mapper

import dsm.pick2024.domain.user.domain.User
import dsm.pick2024.domain.user.entity.UserJpaEntity
import dsm.pick2024.global.base.GenericMapper
import org.springframework.stereotype.Component

@Component
class UserMapper : GenericMapper<UserJpaEntity, User> {

    override fun toEntity(domain: User) = domain.run {
        UserJpaEntity(
            id = id,
            accountId = accountId,
            password = password,
            name = name,
            grade = grade,
            classNum = classNum,
            num = num,
            birthDay = birthDay,
            profile = profile,
            role = role,
            xquareId = xquareId
        )
    }

    override fun toDomain(entity: UserJpaEntity) = entity.run {
        User(
            id = id!!,
            accountId = accountId,
            password = password,
            name = name,
            grade = grade,
            classNum = classNum,
            num = num,
            birthDay = birthDay,
            profile = profile,
            role = role,
            xquareId = xquareId
        )
    }
}
