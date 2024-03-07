package dsm.pick2024.domain.user.port.`in`

import dsm.pick2024.domain.user.presentation.dto.response.QueryUserSimpleInfoResponse

interface QueryUserAllUseCase {
    fun queryUserAll(): List<QueryUserSimpleInfoResponse>
}
