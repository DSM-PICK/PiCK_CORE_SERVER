package dsm.pick2024.domain.afterschool.service

import dsm.pick2024.domain.afterschool.port.`in`.QueryAllUserUseCase
import dsm.pick2024.domain.afterschool.port.out.QueryAfterSchoolStudentAllPort
import dsm.pick2024.domain.user.port.out.UserAllPort
import dsm.pick2024.domain.user.presentation.dto.response.QueryUserSimpleInfoResponse
import org.springframework.stereotype.Service

@Service
class QueryAllSearchStudentService(
    private val findAfterSchoolStudentAllPort: QueryAfterSchoolStudentAllPort,
    private val userAllPort: UserAllPort
) : QueryAllUserUseCase {
    override fun queryAllUser(): List<QueryUserSimpleInfoResponse> {
        val students = findAfterSchoolStudentAllPort.findAll().map { it.userId }
        val users = userAllPort.userAll()

        return users.filterNot { user -> students.contains(user.id) }
            .map {
                QueryUserSimpleInfoResponse(
                    name = it.name,
                    grade = it.grade,
                    classNum = it.classNum,
                    num = it.num
                )
            }
    }
}
