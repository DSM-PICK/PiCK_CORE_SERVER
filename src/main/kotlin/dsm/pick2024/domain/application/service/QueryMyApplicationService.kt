package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.application.enums.ApplicationKind
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.port.`in`.ApplicationFinderUseCase
import dsm.pick2024.domain.application.port.`in`.QueryMyApplicationUseCase
import dsm.pick2024.domain.application.presentation.dto.response.QueryMyApplicationResponse
import dsm.pick2024.domain.applicationstory.enums.Type
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import dsm.pick2024.infrastructure.s3.FileUtil
import dsm.pick2024.infrastructure.s3.PathList
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryMyApplicationService(
    private val userFacadeUseCase: UserFacadeUseCase,
    private val applicationFinderUseCase: ApplicationFinderUseCase,
    private val fileUtil: FileUtil
) : QueryMyApplicationUseCase {

    @Transactional(readOnly = true)
    override fun queryMyApplication(): QueryMyApplicationResponse {
        val user = userFacadeUseCase.currentUser()
        val profileUrl = user.profile?.let { fileUtil.generateObjectUrl(it, PathList.PROFILE) }
        val application =
            applicationFinderUseCase.findByUserIdAndStatusAndApplicationKindOrThrow(
                Status.OK,
                user.id,
                ApplicationKind.APPLICATION
            )

        return QueryMyApplicationResponse(
            userId = application.userId,
            userName = application.userName,
            teacherName = application.teacherName!!,
            start = application.start.take(5),
            end = application.end!!.take(5),
            reason = application.reason,
            profile = profileUrl,
            grade = user.grade,
            classNum = user.classNum,
            num = user.num,
            type = Type.APPLICATION
        )
    }
}
