package dsm.pick2024.domain.user.service

import dsm.pick2024.domain.user.port.`in`.QueryUserSimpleInfoUseCase
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import dsm.pick2024.domain.user.presentation.dto.response.QueryUserSimpleInfoResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryUserSimpleInfoService(
    private val userFacadeUseCase: UserFacadeUseCase
) : QueryUserSimpleInfoUseCase {

    @Transactional(readOnly = true)
    override fun queryUserSimpleInfo(): QueryUserSimpleInfoResponse {
        val user = userFacadeUseCase.currentUser()
        return QueryUserSimpleInfoResponse(user.name, user.grade, user.classNum, user.num)
    }
}
