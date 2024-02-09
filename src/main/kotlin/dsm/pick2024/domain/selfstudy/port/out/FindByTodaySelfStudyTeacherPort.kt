package dsm.pick2024.domain.selfstudy.port.out

import dsm.pick2024.domain.selfstudy.domain.SelfStudy

interface FindByTodaySelfStudyTeacherPort {
    fun findByTodaySelfStudy(): List<SelfStudy>?
}
