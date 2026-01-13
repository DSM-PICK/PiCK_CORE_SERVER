package dsm.pick2024.domain.bug

import dsm.pick2024.domain.bug.presentation.dto.request.BugRequest
import dsm.pick2024.domain.timetable.domain.vo.FileNameVo
import dsm.pick2024.infrastructure.feign.client.DiscordBugClient
import dsm.pick2024.infrastructure.feign.client.dto.request.DiscordMessageRequest
import dsm.pick2024.infrastructure.s3.FileUtil
import dsm.pick2024.infrastructure.s3.PathList
import org.springframework.scheduling.annotation.Async
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class AddBugService(
    private val fileUtil: FileUtil,
    private val discordBugClient: DiscordBugClient
) {

    @Async
    fun bugAlarm(request: BugRequest) {
        val message =
            DiscordMessageRequest(
                content = "# 🚨 버그 제보",
                embeds =
                listOf(
                    DiscordMessageRequest.Embed(
                        title = "ℹ️ 버그 정보",
                        description =
                        """
                                ### 🕖 버그 제목
                                ${request.title}
                                ### 📱유형
                                ${request.model}
                                ### 🔗 버그 내용
                                ${request.content}
                                ### 📄 이미지
                                ${request.fileName?.map { fileUtil.generateObjectUrl(FileNameVo(it), PathList.BUG) } ?: emptyList()}
                                ### 🧑🏻‍💻 버그 제보자
                                ${SecurityContextHolder.getContext().authentication.name}
                        """.trimIndent()
                    )
                )
            )
        discordBugClient.sendMessage(message)
    }
}
