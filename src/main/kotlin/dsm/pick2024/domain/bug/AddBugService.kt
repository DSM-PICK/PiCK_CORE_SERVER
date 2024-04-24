package dsm.pick2024.domain.bug

import dsm.pick2024.domain.bug.presentation.dto.request.BugRequest
import dsm.pick2024.domain.user.facade.UserFacade
import dsm.pick2024.global.discord.DiscordMessage
import dsm.pick2024.infrastructure.feign.client.DiscordBugClient
import dsm.pick2024.infrastructure.s3.FileUtil
import dsm.pick2024.infrastructure.s3.PathList
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class AddBugService(
    private val fileUtil: FileUtil,
    private val discordBugClient: DiscordBugClient,
    private val userFacade: UserFacade
) {
    @Async
    fun bugAlarm(request: BugRequest) {
        val message = DiscordMessage(
            content = "# 🚨 버그 제보",
            embeds = listOf(
                DiscordMessage.Embed(
                    title = "ℹ️ 버그 정보",
                    description = """
                        ### 🕖 버그 제목
                        ${request.title}
                        ### 🔗 버그 내용
                        ${request.content}
                        ### 📄 이미지
                        ```
                        ${request.fileName?.let { fileUtil.generateObjectUrl(request.fileName, PathList.BUG) }}
                        ```
                        ### 🧑🏻‍💻 버그 제보자
                        ${userFacade.currentUser().name}
                    """.trimIndent()
                )
            )
        )
        discordBugClient.sendMessage(message)
    }
}
