package dsm.pick2024.domain.selfstudy.port.out

import java.time.LocalDate

interface DeleteSelfStudyPort {
    fun deleteByDate(date: LocalDate)
}
