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
    override fun findByDateList(date: LocalDate) =
        querySelfStudyPort.findByDateList(date) ?: throw SelfStudyNotFoundException

    override fun findByDaySelfStudy(date: LocalDate) =
        querySelfStudyPort.findByDaySelfStudy(date) ?: throw SelfStudyNotFoundException

    override fun findByMonthSelfStudyTeacher(year: Year, month: Month) =
        querySelfStudyPort.findByMonthSelfStudyTeacher(year, month) ?: throw SelfStudyNotFoundException

    override fun findByTodayTeacher(teacher: String) =
        querySelfStudyPort.findByTodayTeacher(teacher) ?: throw SelfStudyNotFoundException
}
