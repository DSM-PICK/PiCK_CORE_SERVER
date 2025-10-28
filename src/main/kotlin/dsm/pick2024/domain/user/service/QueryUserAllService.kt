package dsm.pick2024.domain.user.service

import dsm.pick2024.domain.user.port.`in`.QueryUserAllUseCase
import dsm.pick2024.domain.user.port.`in`.UserFinderUseCase
import dsm.pick2024.domain.user.presentation.dto.response.QueryUserSimpleInfoResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryUserAllService(
    private val userFinderUseCase: UserFinderUseCase
) : QueryUserAllUseCase {

    @Transactional(readOnly = true)
    override fun queryUserAll() =
        userFinderUseCase.userAllOrThrow()
            .map {
                QueryUserSimpleInfoResponse(
                    userName = it.name,
                    grade = it.grade,
                    classNum = it.classNum,
                    num = it.num
                )
            }.sortedWith(compareBy({ it.grade }, { it.classNum }, { it.num }))
}
