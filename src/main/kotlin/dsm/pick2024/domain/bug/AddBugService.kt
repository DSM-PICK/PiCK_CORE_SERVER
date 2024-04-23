package dsm.pick2024.domain.bug

import dsm.pick2024.domain.bug.presentation.dto.request.BugRequest
import dsm.pick2024.infrastructure.feign.client.DiscordMessageClient
import dsm.pick2024.infrastructure.s3.FileUtil
import dsm.pick2024.infrastructure.s3.PathList
import org.springframework.stereotype.Service

@Service
class AddBugService(
    private val fileUtil: FileUtil,
    private val discordMessageClient: DiscordMessageClient
) {
    fun bugAlarm(request: BugRequest) {
        request.fileName?.let {
            fileUtil.generateObjectUrl(request.fileName, PathList.BUG)
        }

        discordMessageClient.sendMessage(request)
    }
}
