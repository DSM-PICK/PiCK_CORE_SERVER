package dsm.pick2024.domain.user.persistence

import dsm.pick2024.domain.user.model.GoogleUserInfo
import dsm.pick2024.domain.user.port.out.GoogleOAuthPort
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate

@Component
class GoogleOAuthAdapter(
    @Value("\${google.client-id}") private val clientId: String,
    @Value("\${google.client-secret}") private val clientSecret: String,
    @Value("\${google.redirect-uri}") private val redirectUri: String,
    private val restTemplate: RestTemplate = RestTemplate()
) : GoogleOAuthPort {

    override fun getAccessToken(code: String): String {
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_FORM_URLENCODED
        }

        val body = LinkedMultiValueMap<String, String>().apply {
            add("code", code)
            add("client_id", clientId)
            add("client_secret", clientSecret)
            add("redirect_uri", redirectUri)
            add("grant_type", "authorization_code")
        }

        val entity = HttpEntity(body, headers)

        val response = restTemplate.postForEntity(
            "https://oauth2.googleapis.com/token",
            entity,
            Map::class.java
        )


        if (response.statusCode != HttpStatus.OK) {
            throw IllegalStateException("Google 토큰 요청 실패: ${response.statusCode}")
        }

        return response.body?.get("access_token") as? String
            ?: throw IllegalStateException("access_token 누락")
    }



    override fun getUserInfo(accessToken: String): GoogleUserInfo {
        val headers = HttpHeaders().apply {
            setBearerAuth(accessToken)
        }

        val entity = HttpEntity<Unit>(headers)

        val response = restTemplate.exchange(
            "https://www.googleapis.com/oauth2/v2/userinfo",
            HttpMethod.GET,
            entity,
            GoogleUserInfo::class.java
        )

        return response.body ?: throw IllegalStateException("유저 정보 응답 없음")
    }
}
