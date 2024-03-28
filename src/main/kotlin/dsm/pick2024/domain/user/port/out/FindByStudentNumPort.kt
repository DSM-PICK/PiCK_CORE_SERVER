package dsm.pick2024.domain.user.port.out

import dsm.pick2024.domain.user.domain.User

interface FindByStudentNumPort {
    fun findByStudentNum(
        grade: Int,
        classNum: Int,
        num: Int
    ): User?
}
