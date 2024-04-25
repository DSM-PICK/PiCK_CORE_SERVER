package dsm.pick2024.infrastructure.discord

import dsm.pick2024.infrastructure.feign.client.dto.request.DiscordMessageRequest.Embed
import dsm.pick2024.infrastructure.feign.client.DiscordProdClient
import dsm.pick2024.infrastructure.feign.client.DiscordStagClient
import dsm.pick2024.infrastructure.feign.client.dto.request.DiscordMessageRequest
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest
import java.io.PrintWriter
import java.io.StringWriter
import java.time.LocalDateTime

@Component
class DiscordComponet(
    private val discordStagClient: DiscordStagClient,
    private val discordProdClient: DiscordProdClient,
    private val environment: Environment
) {
    fun sendDiscordAlarm(e: Exception, request: WebRequest) {
        if (isProductionEnvironment()) {
            discordProdClient.prodSendAlarm(createMessage(e, request))
        } else {
            discordStagClient.stagSendAlarm(createMessage(e, request))
        }
    }

    private fun isProductionEnvironment(): Boolean {
        val activeProfiles = environment.activeProfiles
        return "prod" in activeProfiles
    }

    private fun createMessage(e: Exception, request: WebRequest): DiscordMessageRequest {
        return DiscordMessageRequest(
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
