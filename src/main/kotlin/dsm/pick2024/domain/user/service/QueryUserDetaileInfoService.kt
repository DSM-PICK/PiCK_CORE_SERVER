package dsm.pick2024.domain.user.service

import dsm.pick2024.domain.user.port.`in`.QueryUserDetailsInfoUseCase
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import dsm.pick2024.domain.user.presentation.dto.response.QueryUserDetailsInfoResponse
import dsm.pick2024.infrastructure.s3.FileUtil
import dsm.pick2024.infrastructure.s3.PathList
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryUserDetaileInfoService(
    private val userFacadeUseCase: UserFacadeUseCase,
    private val fileUtil: FileUtil
) : QueryUserDetailsInfoUseCase {

    @Transactional(readOnly = true)
    override fun queryUserDetailsInfo(): QueryUserDetailsInfoResponse {
        val user = userFacadeUseCase.currentUser()

        val profileUrl = user.profile?.let { fileUtil.generateObjectUrl(it, PathList.PROFILE) }

        return QueryUserDetailsInfoResponse(
            profile = profileUrl?.substringBefore("?"),
            userName = user.name,
            grade = user.grade,
            classNum = user.classNum,
            num = user.num,
            accountId = user.accountId
        )
    }
}
