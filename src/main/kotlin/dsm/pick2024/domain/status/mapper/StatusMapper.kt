package dsm.pick2024.domain.status.mapper

import dsm.pick2024.domain.status.domain.Status
import dsm.pick2024.domain.status.entity.StatusJpaEntity
import org.springframework.stereotype.Component

@Component
class StatusMapper {
    fun toEntity(domain: Status): StatusJpaEntity =
        domain.run {
            StatusJpaEntity(
                id = id,
                userId = userId,
                username = username,
                grade = grade,
                classNum = classNum,
                num = num,
                period6 = period6,
                period7 = period7,
                period8 = period8,
                period9 = period9,
                period10 = period10
            )
        }

    fun toDomain(entity: StatusJpaEntity): Status =
        entity.run {
            Status(
                id = id,
                userId = userId,
                username = username,
                grade = grade,
                classNum = classNum,
                num = num,
                period6 = period6,
                period7 = period7,
                period8 = period8,
                period9 = period9,
                period10 = period10
            )
        }
}
