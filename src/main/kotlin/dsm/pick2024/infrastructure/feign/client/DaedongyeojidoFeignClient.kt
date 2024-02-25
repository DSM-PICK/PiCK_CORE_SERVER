package dsm.pick2024.infrastructure.feign.client

import dsm.pick2024.infrastructure.feign.client.response.UserResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(name = "daedongyeojido-feign-client", url = "\${url.daedongyeojido}")
interface DaedongyeojidoFeignClient {

    @GetMapping("user/all")
    fun userAll(
        @RequestBody pickSecretKey: String
    ): List<UserResponse>
}
