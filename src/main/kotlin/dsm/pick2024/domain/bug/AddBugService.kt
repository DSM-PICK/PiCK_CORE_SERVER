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
        val messageTitle = "Bug Report: ${request.title}"
        val messageContent = buildString {
            append("Title: ${request.title}\n")
            append("Content: ${request.content}\n")
            if (request.fileName != null) {
                val fileUrl = fileUtil.generateObjectUrl(request.fileName, PathList.BUG)
                append("File Name: ${request.fileName}\nFile URL: $fileUrl")
            }
        }

        val message = mapOf("message" to messageTitle, "content" to messageContent)
        discordMessageClient.sendMessage(message)
    }
}
