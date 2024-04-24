package dsm.pick2024.infrastructure.feign.client

import dsm.pick2024.global.discord.DiscordMessage
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(name = "discord-bug-prod-client", url = "\${discord.webhook.prod}")
interface DiscordProdClient {
    @PostMapping
    fun prodSendAlarm(@RequestBody message: DiscordMessage)
}
