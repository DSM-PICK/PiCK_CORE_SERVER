package dsm.pick2024.domain.selfstudy.port.out

import dsm.pick2024.domain.selfstudy.domain.SelfStudy
import java.time.LocalDate

interface FindByMonthSelfStudyTeacherPort {
    fun findByMonthSelfStudyTeacher(date: LocalDate): List<SelfStudy>
}
