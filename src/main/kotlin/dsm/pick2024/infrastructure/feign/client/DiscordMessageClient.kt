package dsm.pick2024.infrastructure.feign.client

import dsm.pick2024.domain.bug.presentation.dto.request.BugRequest
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(name = "feign-message", url = "\${discord.webhook.message}")
interface DiscordMessageClient {
    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun sendMessage(
        @RequestBody bugRequest: BugRequest
    )
}
