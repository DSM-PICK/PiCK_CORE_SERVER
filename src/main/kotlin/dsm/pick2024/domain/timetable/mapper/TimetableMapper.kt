package dsm.pick2024.domain.timetable.mapper

import dsm.pick2024.domain.timetable.domain.Timetable
import dsm.pick2024.domain.timetable.entity.TimetableJpaEntity
import org.springframework.stereotype.Component

@Component
class TimetableMapper {
    fun toEntity(domain: Timetable) =
        domain.run {
            TimetableJpaEntity(
                id = id,
                grade = grade,
                classNum = classNum,
                period = period,
                subjectName = subjectName,
                dayWeek = dayWeek
            )
        }

    fun toDomain(entity: TimetableJpaEntity) =
        entity.run {
            Timetable(
                id = id,
                grade = grade,
                classNum = classNum,
                period = period,
                subjectName = subjectName,
                dayWeek = dayWeek
            )
        }
}
