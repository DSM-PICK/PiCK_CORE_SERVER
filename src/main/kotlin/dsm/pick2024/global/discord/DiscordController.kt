package dsm.pick2024.global.discord

import dsm.pick2024.global.discord.DiscordMessage.Embed
import dsm.pick2024.infrastructure.feign.client.DiscordClient
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest
import java.io.PrintWriter
import java.io.StringWriter
import java.time.LocalDateTime

@RestControllerAdvice
class DiscordController(
    private val discordClient: DiscordClient
) {
    fun sendDiscordAlarm(e: Exception, request: WebRequest) {
        discordClient.sendAlarm(createMessage(e, request))
    }

    private fun createMessage(e: Exception, request: WebRequest): DiscordMessage {
        return DiscordMessage(
            content = "# 🚨 에러 발생",
            embeds = listOf(
                Embed(
                    title = "ℹ️ 에러 정보",
                    description = """
                        ### 🕖 발생 시간
                        ${LocalDateTime.now()}
                        ### 🔗 요청 URL
                        ${createRequestFullPath(request)}
                        ### 📄 Stack Trace
                        ```
                        ${getStackTrace(e).substring(0, 1000)}
                        ```
                    """.trimIndent()
                )
            )
        )
    }

    private fun createRequestFullPath(webRequest: WebRequest): String {
        val request = (webRequest as ServletWebRequest).request
        var fullPath = "${request.method} ${request.requestURL}"

        val queryString = request.queryString
        if (queryString != null) {
            fullPath += "?$queryString"
        }

        return fullPath
    }

    private fun getStackTrace(e: Exception): String {
        val stringWriter = StringWriter()
        e.printStackTrace(PrintWriter(stringWriter))
        return stringWriter.toString()
    }
}
