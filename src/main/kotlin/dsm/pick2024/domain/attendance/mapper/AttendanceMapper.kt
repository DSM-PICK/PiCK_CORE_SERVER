package dsm.pick2024.domain.attendance.mapper

import dsm.pick2024.domain.attendance.domain.Attendance
import dsm.pick2024.domain.attendance.entity.AttendanceJpaEntity
import dsm.pick2024.global.base.GenericMapper
import org.springframework.stereotype.Component

@Component
class AttendanceMapper : GenericMapper<AttendanceJpaEntity, Attendance> {
    override fun toEntity(domain: Attendance) =
        domain.run {
            AttendanceJpaEntity(
                id = id,
                userId = userId,
                grade = grade,
                classNum = classNum,
                num = num,
                name = name,
                club = club,
                period6 = period6,
                period7 = period7,
                period8 = period8,
                period9 = period9,
                period10 = period10
            )
        }

    override fun toDomain(entity: AttendanceJpaEntity) =
        entity.run {
            Attendance(
                id = id,
                userId = userId,
                grade = grade,
                classNum = classNum,
                num = num,
                name = name,
                club = club!!,
                period6 = period6,
                period7 = period7,
                period8 = period8,
                period9 = period9,
                period10 = period10
            )
        }
}
