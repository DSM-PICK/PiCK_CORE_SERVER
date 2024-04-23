package dsm.pick2024.infrastructure.feign.client

import dsm.pick2024.domain.bug.presentation.dto.request.DiscordMessage
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(url = "\${discord.webhook.message}")
interface DiscordMessageClient {
    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun sendMessage(
        @RequestBody discordMessage: DiscordMessage
    )
}
