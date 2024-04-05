package dsm.pick2024.domain.afterschool.port.`in`

import dsm.pick2024.domain.user.presentation.dto.response.QueryUserSimpleInfoResponse

interface QueryAllUserUseCase {
    fun queryAllUser(): List<QueryUserSimpleInfoResponse>
}
