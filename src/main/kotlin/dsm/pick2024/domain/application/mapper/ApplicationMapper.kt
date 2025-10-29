package dsm.pick2024.domain.application.mapper

import dsm.pick2024.domain.application.domain.Application
import dsm.pick2024.domain.application.entity.ApplicationJpaEntity
import dsm.pick2024.domain.user.entity.UserJpaEntity
import org.springframework.stereotype.Component

@Component
class ApplicationMapper {
    fun toEntity(domain: Application, user: UserJpaEntity) =
        domain.run {
            ApplicationJpaEntity(
                id = id,
                user = user,
                reason = reason,
                start = start,
                end = end,
                userName = userName,
                status = status,
                teacherName = teacherName,
                date = date,
                grade = grade,
                classNum = classNum,
                num = num,
                applicationType = applicationType,
                applicationKind = applicationKind
            )
        }

    fun toDomain(entity: ApplicationJpaEntity) =
        entity.run {
            Application(
                id = id!!,
                userId = user.id!!,
                reason = reason,
                start = start,
                end = end,
                userName = userName,
                status = status,
                teacherName = teacherName,
                date = date,
                grade = grade,
                classNum = classNum,
                num = num,
                applicationType = applicationType,
                applicationKind = applicationKind
            )
        }
}
