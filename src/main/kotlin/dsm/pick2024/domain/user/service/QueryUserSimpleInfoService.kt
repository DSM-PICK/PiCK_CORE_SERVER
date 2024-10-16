package dsm.pick2024.domain.user.service

import dsm.pick2024.domain.user.port.`in`.QueryUserSimpleInfoUseCase
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import dsm.pick2024.domain.user.presentation.dto.response.QueryUserSimpleInfoResponse
import dsm.pick2024.infrastructure.s3.FileUtil
import dsm.pick2024.infrastructure.s3.PathList
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryUserSimpleInfoService(
    private val userFacadeUseCase: UserFacadeUseCase,
    private val fileUtil: FileUtil
) : QueryUserSimpleInfoUseCase {

    @Transactional(readOnly = true)
    override fun queryUserSimpleInfo(): QueryUserSimpleInfoResponse {
        val user = userFacadeUseCase.currentUser()
        return QueryUserSimpleInfoResponse(
            user.name,
            user.grade,
            user.classNum,
            user.num,
            user.profile?.let { fileUtil.generateObjectUrl(it, PathList.PROFILE) }
        )
    }
}
