package dsm.pick2024.domain.user.service

import dsm.pick2024.domain.user.port.`in`.QueryUserDetailsInfoUseCase
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import dsm.pick2024.domain.user.presentation.dto.response.QueryUserDetailsInfoResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryUserDetaileInfoService(
    private val userFacadeUseCase: UserFacadeUseCase
): QueryUserDetailsInfoUseCase {

    @Transactional(readOnly = true)
    override fun queryUserDetailsInfo(): QueryUserDetailsInfoResponse {
        val user = userFacadeUseCase.currentUser()
        return QueryUserDetailsInfoResponse(user.profile, user.name, user.birthDay, user.grade, user.classNum, user.num, user.accountId)
    }
}
