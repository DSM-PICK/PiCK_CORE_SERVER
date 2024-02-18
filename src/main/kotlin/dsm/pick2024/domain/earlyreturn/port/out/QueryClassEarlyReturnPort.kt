package dsm.pick2024.domain.earlyreturn.port.out

import dsm.pick2024.domain.earlyreturn.domain.EarlyReturn

interface QueryClassEarlyReturnPort {
    fun findByGradeAndClassNum(grade: Int, classNum: Int): List<EarlyReturn>
}
