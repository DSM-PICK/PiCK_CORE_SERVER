package dsm.pick2024.infrastructure.feign.fcm

import dsm.pick2024.domain.fcm.domain.FcmMessage
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(name = "fcm", url = "https://fcm.googleapis.com")
interface FcmClient {

    @PostMapping("/v1/projects/pick-v2-34883/messages:send")
    fun sendMessage(
        @RequestHeader("Authorization") authorization: String,
        @RequestBody request: FcmMessage
    ): Unit
}
