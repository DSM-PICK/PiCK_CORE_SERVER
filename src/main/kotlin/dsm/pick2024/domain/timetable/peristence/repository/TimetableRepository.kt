package dsm.pick2024.domain.timetable.peristence.repository

import dsm.pick2024.domain.timetable.entity.TimetableJpaEntity
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository

interface TimetableRepository : JpaRepository<TimetableJpaEntity, UUID> {
    fun findByDayWeekAndPeriodAndGradeAndClassNum(
        dayOfWeek: Int,
        period: Int,
        grade: Int,
        classNum: Int
    ): TimetableJpaEntity
}
