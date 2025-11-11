package dsm.pick2024.domain.selfstudy.finder

import dsm.pick2024.domain.selfstudy.exception.SelfStudyNotFoundException
import dsm.pick2024.domain.selfstudy.port.`in`.SelfStudyFinderUseCase
import dsm.pick2024.domain.selfstudy.port.out.QuerySelfStudyPort
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.Month
import java.time.Year

@Component
class SelfStudyFinder(
    private val querySelfStudyPort: QuerySelfStudyPort
) : SelfStudyFinderUseCase {
    override fun findByDateListOrThrow(date: LocalDate) =
        querySelfStudyPort.findByDateList(date) ?: throw SelfStudyNotFoundException

    override fun findByDaySelfStudyOrThrow(date: LocalDate) =
        querySelfStudyPort.findByDaySelfStudy(date) ?: throw SelfStudyNotFoundException

    override fun findByMonthSelfStudyTeacherOrThrow(year: Year, month: Month) =
        querySelfStudyPort.findByMonthSelfStudyTeacher(year, month) ?: throw SelfStudyNotFoundException

    override fun findByTodayTeacherOrThrow(teacher: String) =
        querySelfStudyPort.findByTodayTeacher(teacher) ?: throw SelfStudyNotFoundException
}
