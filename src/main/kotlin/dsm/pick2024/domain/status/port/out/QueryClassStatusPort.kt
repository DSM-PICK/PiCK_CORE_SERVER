package dsm.pick2024.domain.status.port.out

import dsm.pick2024.domain.status.domain.Status

interface QueryClassStatusPort {
    fun findByGradeAndClassNum(grade: Int, classNum: Int): List<Status>
}
