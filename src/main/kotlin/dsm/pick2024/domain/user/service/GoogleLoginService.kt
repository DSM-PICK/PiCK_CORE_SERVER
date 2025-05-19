package dsm.pick2024.domain.user.service

import dsm.pick2024.domain.user.domain.User
import dsm.pick2024.domain.user.model.GoogleUserInfo
import dsm.pick2024.domain.user.port.`in`.GoogleLoginUseCase
import dsm.pick2024.domain.user.port.out.GoogleOAuthPort
import dsm.pick2024.domain.user.port.out.UserPort
import dsm.pick2024.global.security.jwt.JwtTokenProvider
import dsm.pick2024.global.security.jwt.dto.TokenResponse
import org.springframework.stereotype.Service
import java.nio.file.AccessDeniedException

@Service
class GoogleLoginService(
    private val googleOAuthPort: GoogleOAuthPort,
    private val userPort: UserPort,
    private val jwtProvider: JwtTokenProvider
) : GoogleLoginUseCase {

    override fun loginByGoogle(code: String): TokenResponse {

        val accessToken = googleOAuthPort.getAccessToken(code)

        val userInfo: GoogleUserInfo = googleOAuthPort.getUserInfo(accessToken)

        if (!userInfo.email.endsWith("@dsm.hs.kr")) {
            throw AccessDeniedException("DSM 이메일만 로그인 가능합니다.")
        }


        val user: User = if (userPort.existsByAccountId(userInfo.email)) {
            userPort.findByAccountId(userInfo.email)
                ?: throw IllegalStateException("사용자가 존재하지 않음")
        } else {
            userPort.save(
                User.fromGoogle(
                    accountId = userInfo.email,
                    name = userInfo.name,
                    profile = null
                )
            )
        }


        val token = jwtProvider.generateToken(
            user.id!!.toString(),
            user.role.name
        )

        return TokenResponse(
            accessToken = token.accessToken,
            refreshToken = token.refreshToken
        )
    }

}
