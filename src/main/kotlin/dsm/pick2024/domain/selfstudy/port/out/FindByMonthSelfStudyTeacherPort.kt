package dsm.pick2024.domain.selfstudy.port.out

import dsm.pick2024.domain.selfstudy.domain.SelfStudy
import java.time.LocalDate
import java.time.Month
import java.time.Year

interface FindByMonthSelfStudyTeacherPort {
    fun findByMonthSelfStudyTeacher(year: Year, month: Month): List<SelfStudy>
}
