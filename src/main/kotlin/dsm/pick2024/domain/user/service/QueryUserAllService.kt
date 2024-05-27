package dsm.pick2024.domain.user.service

import dsm.pick2024.domain.user.port.`in`.QueryUserAllUseCase
import dsm.pick2024.domain.user.port.out.UserAllPort
import dsm.pick2024.domain.user.presentation.dto.response.QueryUserSimpleInfoResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryUserAllService(
    private val userAllPort: UserAllPort
) : QueryUserAllUseCase {

    @Transactional(readOnly = true)
    override fun queryUserAll() =
        userAllPort.userAll()
            .map {
                    it ->
                QueryUserSimpleInfoResponse(
                    name = it.name,
                    grade = it.grade,
                    classNum = it.classNum,
                    num = it.num
                )
            }
}
