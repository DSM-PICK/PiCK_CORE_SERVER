package dsm.pick2024.domain.earlyreturn.port.out

import dsm.pick2024.domain.earlyreturn.domain.EarlyReturn
import java.util.*

interface QueryEarlyReturnPort {

    fun findById(earlyReturnId: UUID): EarlyReturn?

    fun findByOKEarlyReturn(userId: UUID): EarlyReturn?

    fun findByFloor(floor: Int): List<EarlyReturn>

    fun findByGradeAndClassNum(grade: Int, classNum: Int): List<EarlyReturn>

}
