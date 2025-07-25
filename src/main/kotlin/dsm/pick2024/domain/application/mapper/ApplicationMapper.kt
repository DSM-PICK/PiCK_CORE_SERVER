package dsm.pick2024.domain.application.mapper

import dsm.pick2024.domain.application.domain.Application
import dsm.pick2024.domain.application.entity.ApplicationJapEntity
import org.springframework.stereotype.Component

@Component
class ApplicationMapper {
    fun toEntity(domain: Application) =
        domain.run {
            ApplicationJapEntity(
                id = id,
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
                userId = userId,
                applicationType = applicationType,
                applicationKind = applicationKind
            )
        }

    fun toDomain(entity: ApplicationJapEntity) =
        entity.run {
            Application(
                id = id!!,
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
                userId = userId,
                applicationType = applicationType,
                applicationKind = applicationKind
            )
        }
}
