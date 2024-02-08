package dsm.pick2024.domain.application.mapper

import dsm.pick2024.domain.application.domain.Application
import dsm.pick2024.domain.application.entity.ApplicationJapEntity
import org.springframework.stereotype.Component

@Component
class ApplicationMapper {
    fun toEntity(domain: Application): ApplicationJapEntity =
        domain.run {
            ApplicationJapEntity(
                id = id,
                reason = reason,
                startTime = startTime,
                endTime = endTime,
                username = username,
                status = status,
                teacherName = teacherName
            )
        }

    fun toDomain(entity: ApplicationJapEntity): Application =
        entity.run {
            Application(
                id = id,
                reason = reason,
                startTime = startTime,
                endTime = endTime,
                username = username,
                status = status,
                teacherName = teacherName
            )
        }
}
