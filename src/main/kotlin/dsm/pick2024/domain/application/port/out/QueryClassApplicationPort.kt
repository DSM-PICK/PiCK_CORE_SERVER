package dsm.pick2024.domain.application.port.out

import dsm.pick2024.domain.application.domain.Application

interface QueryClassApplicationPort {
    fun findByGradeAndClassNum(grade: Int, classNum: Int): List<Application>
}
