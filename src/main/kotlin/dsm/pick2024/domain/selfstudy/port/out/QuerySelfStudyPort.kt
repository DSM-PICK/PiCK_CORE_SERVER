package dsm.pick2024.domain.selfstudy.port.out

import dsm.pick2024.domain.selfstudy.domain.SelfStudy
import java.time.LocalDate
import java.time.Month
import java.time.Year

interface QuerySelfStudyPort {

    fun findByDateList(date: LocalDate): List<SelfStudy>?

    fun findByDaySelfStudy(date: LocalDate): List<SelfStudy>?

    fun findByMonthSelfStudyTeacher(year: Year, month: Month): List<SelfStudy>?

    fun findByTodayTeacher(teacher: String): SelfStudy?
}
