package dsm.pick2024.global.security.jwt

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtTokenFilter(
    private val jwtTokenProvider: JwtTokenProvider
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val bearer: String? = jwtTokenProvider.resolveToken(request)

        if (bearer != null) {
            val authentication: Authentication = jwtTokenProvider.authorization(bearer)
            authentication.also { SecurityContextHolder.getContext().authentication = it }
        }
        filterChain.doFilter(request, response)
    }
}
