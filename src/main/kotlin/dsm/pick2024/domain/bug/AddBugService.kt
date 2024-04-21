package dsm.pick2024.domain.bug

import dsm.pick2024.domain.user.facade.UserFacade
import dsm.pick2024.infrastructure.s3.FileUtil
import dsm.pick2024.infrastructure.s3.PathList
import org.springframework.stereotype.Service

@Service
class AddBugService(
    private val userFacade: UserFacade,
    private val adminFacade: UserFacade,
    private val fileUtil: FileUtil
) {
    fun bugAlarm(request: BugRequest) {
        val file = request.fileName?.let {
                 fileUtil.generateObjectUrl(request.fileName, PathList.BUG)
            }

    }
}
