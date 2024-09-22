package dsm.pick2024.domain.user.service

import dsm.pick2024.domain.user.port.`in`.QueryUserAllUseCase
import dsm.pick2024.domain.user.port.out.QueryUserPort
import dsm.pick2024.domain.user.presentation.dto.response.QueryUserSimpleInfoResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryUserAllService(
    private val queryUserPort: QueryUserPort
) : QueryUserAllUseCase {

    @Transactional(readOnly = true)
    override fun queryUserAll() =
        queryUserPort.userAll()
            .map {
                    it ->
                QueryUserSimpleInfoResponse(
                    userName = it.name,
                    grade = it.grade,
                    classNum = it.classNum,
                    num = it.num
                )
            }.sortedWith(compareBy({ it.grade }, { it.classNum }, { it.num }))
}
