package dsm.pick2024.domain.applicationstory.mapper

import dsm.pick2024.domain.applicationstory.domain.ApplicationStory
import dsm.pick2024.domain.applicationstory.entity.ApplicationStoryJpaEntity
import dsm.pick2024.domain.user.entity.UserJpaEntity
import org.springframework.stereotype.Component

@Component
class ApplicationStoryMapper {
    fun toEntity(domain: ApplicationStory, user: UserJpaEntity) =
        domain.run {
            ApplicationStoryJpaEntity(
                id = id,
                reason = reason,
                start = start,
                end = end,
                userName = userName,
                date = date,
                type = type,
                user = user,
                teacherName = returnTeacherName
            )
        }

    fun toDomain(entity: ApplicationStoryJpaEntity) =
        entity.run {
            ApplicationStory(
                id = id!!,
                reason = reason,
                start = start,
                end = end,
                userName = userName,
                date = date,
                type = type,
                userId = user.id!!,
                returnTeacherName = teacherName
            )
        }
}
