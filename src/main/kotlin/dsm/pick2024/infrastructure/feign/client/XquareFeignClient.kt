package dsm.pick2024.infrastructure.feign.client

import dsm.pick2024.domain.user.presentation.dto.request.UserLoginRequest
import dsm.pick2024.infrastructure.feign.client.dto.response.XquareResponse
import dsm.pick2024.infrastructure.feign.client.dto.response.XquareUserAllResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "xquare-login", url = "\${url.dsmlogin}")
interface XquareFeignClient {

    @GetMapping("/user-data")
    fun xquareUser(
        @RequestBody userLoginRequest: UserLoginRequest
    ): XquareResponse

    @GetMapping("/user-all")
    fun userAll(
        @RequestParam(name = "secret_key") key: String
    ): List<XquareUserAllResponse>
}
