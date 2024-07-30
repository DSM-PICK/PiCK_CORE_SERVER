package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.application.exception.ApplicationNotFoundException
import dsm.pick2024.domain.application.port.`in`.QueryMyApplicationUseCase
import dsm.pick2024.domain.application.port.out.QueryApplicationPort
import dsm.pick2024.domain.application.presentation.dto.response.QueryMyApplicationResponse
import dsm.pick2024.domain.applicationstory.enums.Type
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import dsm.pick2024.infrastructure.s3.FileUtil
import dsm.pick2024.infrastructure.s3.PathList
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.format.DateTimeFormatter

@Service
class QueryMyApplicationService(
    private val userFacadeUseCase: UserFacadeUseCase,
    private val queryApplicationPort: QueryApplicationPort,
    private val fileUtil: FileUtil
) : QueryMyApplicationUseCase {

    @Transactional(readOnly = true)
    override fun queryMyApplication(): QueryMyApplicationResponse {
        val user = userFacadeUseCase.currentUser()
        val application =
            queryApplicationPort.findOKApplication(user.id)
                ?: throw ApplicationNotFoundException

        return QueryMyApplicationResponse(

            userId = application.userId,
            username = application.userName,
            teacherName = application.teacherName!!,
            startTime = application.startTime.format(DateTimeFormatter.ofPattern("HH:mm")),
            endTime = application.endTime.format(DateTimeFormatter.ofPattern("HH:mm")),
            reason = application.reason,
            schoolNum = (user.grade * 1000) + (user.classNum * 100) + user.num,
            type = Type.APPLICATION,
            profile = user.profile?.let { fileUtil.generateObjectUrl(it, PathList.PROFILE) }
        )
    }
}
