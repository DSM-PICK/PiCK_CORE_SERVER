package dsm.pick2024.domain.selfstudy.port.`in`

import dsm.pick2024.domain.selfstudy.domain.SelfStudy
import java.time.LocalDate
import java.time.Month
import java.time.Year

interface SelfStudyFinderUseCase {

    fun findByDateListOrThrow(date: LocalDate): List<SelfStudy>

    fun findByDaySelfStudyOrThrow(date: LocalDate): List<SelfStudy>

    fun findByMonthSelfStudyTeacherOrThrow(year: Year, month: Month): List<SelfStudy>

    fun findByTodayTeacherOrThrow(teacher: String): SelfStudy
}
