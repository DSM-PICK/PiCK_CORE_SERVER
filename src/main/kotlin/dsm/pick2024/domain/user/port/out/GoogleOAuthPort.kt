package dsm.pick2024.domain.user.port.out

import dsm.pick2024.domain.user.model.GoogleUserInfo

interface GoogleOAuthPort {
    fun getAccessToken(code: String): String
    fun getUserInfo(accessToken: String): GoogleUserInfo
}
