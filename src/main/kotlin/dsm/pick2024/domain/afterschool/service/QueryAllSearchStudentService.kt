package dsm.pick2024.domain.afterschool.service

import dsm.pick2024.domain.afterschool.port.`in`.QueryAllUserUseCase
import dsm.pick2024.domain.afterschool.port.out.QueryAfterSchoolStudentPort
import dsm.pick2024.domain.user.port.`in`.UserFinderUseCase
import dsm.pick2024.domain.user.presentation.dto.response.QueryUserSimpleInfoResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryAllSearchStudentService(
    private val queryAfterSchoolPort: QueryAfterSchoolStudentPort,
    private val userFinderUseCase: UserFinderUseCase
) : QueryAllUserUseCase {

    @Transactional(readOnly = true)
    override fun queryAllUser(): List<QueryUserSimpleInfoResponse> {
        val students = queryAfterSchoolPort.findAll().map { it.userId }
        val users = userFinderUseCase.userAllOrThrow()

        return users.filterNot { user -> students.contains(user.id) }
            .map {
                QueryUserSimpleInfoResponse(
                    userName = it.name,
                    grade = it.grade,
                    classNum = it.classNum,
                    num = it.num
                )
            }.sortedWith(compareBy({ it.grade }, { it.classNum }, { it.num }))
    }
}
