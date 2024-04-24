package dsm.pick2024.infrastructure.feign.client

import dsm.pick2024.global.discord.DiscordMessage
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(name = "discord-bug-message-client", url = "\${discord.webhook.message}")
interface DiscordBugClient {
    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun sendMessage(
        @RequestBody message: DiscordMessage
    )
}
