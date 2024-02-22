package dsm.pick2024.domain.selfstudy.port.out

import dsm.pick2024.domain.selfstudy.domain.SelfStudy
import java.time.LocalDate

interface SelfStudyByDateAndFloor {
    fun findByDate(date: LocalDate, floor: Int): SelfStudy?
}
