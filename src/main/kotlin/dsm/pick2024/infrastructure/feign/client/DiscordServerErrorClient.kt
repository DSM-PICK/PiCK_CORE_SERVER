package dsm.pick2024.infrastructure.feign.client

import dsm.pick2024.infrastructure.feign.client.dto.request.DiscordMessageRequest
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(name = "discord-server-error-message-client", url = "\${discord.webhook.errorMessage}")
interface DiscordServerErrorClient {
    @PostMapping
    fun sendMessage(
        @RequestBody message: DiscordMessageRequest
    )
}
