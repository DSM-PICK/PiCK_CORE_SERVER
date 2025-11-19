package dsm.pick2024.infrastructure.feign.client

import dsm.pick2024.infrastructure.feign.client.dto.request.DiscordMessageRequest
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(name = "discord-bug-message-client", url = "\${discord.webhook.bugMessage}")
interface DiscordBugClient {
    @PostMapping
    fun sendMessage(
        @RequestBody message: DiscordMessageRequest
    )
}
