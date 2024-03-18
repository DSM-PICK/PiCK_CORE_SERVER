package dsm.pick2024.infrastructure.feign.client

import dsm.pick2024.infrastructure.feign.client.response.XquareResponse
import dsm.pick2024.infrastructure.feign.client.response.XquareUserAllResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "xquare-login", url = "\${url.dsmlogin}")
interface XquareFeignClient {

    @GetMapping("/user-data")
    fun xquareUser(
        @RequestParam("account_id")
        accountId: String,
        @RequestParam("password")
        password: String
    ): XquareResponse

    @GetMapping("/user-all")
    fun userAll(
        @RequestParam(name = "secret_key") key: String
    ): List<XquareUserAllResponse>
}
