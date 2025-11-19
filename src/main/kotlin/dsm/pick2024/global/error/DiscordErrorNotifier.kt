package dsm.pick2024.global.error

import dsm.pick2024.infrastructure.feign.client.DiscordServerErrorClient
import org.springframework.stereotype.Component
import dsm.pick2024.infrastructure.feign.client.dto.request.DiscordMessageRequest
import org.springframework.scheduling.annotation.Async

@Component
class DiscordErrorNotifier(
    private val discordServerErrorClient: DiscordServerErrorClient
) {

    @Async
    fun sendError(url: String, message: String, location: String, requestBody: String?) {
        val discordRequest = DiscordMessageRequest(
            content = "# 🚨 500 INTERNAL SERVER ERROR",
            embeds = listOf(
                DiscordMessageRequest.Embed(
                    title = "🔥 오류 발생",
                    description = """
                        ### 🔗 URL
                        $url
                        
                        ### 📍 Location
                        $location
                        
                        ### 💬 Message
                        $message
                        
                        ### 📦 Request Body
                        ${requestBody ?: "N/A"}
                    """.trimIndent()
                )
            )
        )

        discordServerErrorClient.sendMessage(discordRequest)
    }
}
