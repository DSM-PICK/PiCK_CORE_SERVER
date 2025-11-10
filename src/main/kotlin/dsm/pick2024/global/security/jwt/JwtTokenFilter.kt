package dsm.pick2024.global.security.jwt

import io.jsonwebtoken.JwtException
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
        val token = jwtTokenProvider.resolveToken(request)

        token?.let {
            try {
                val authentication = jwtTokenProvider.authentication(it)
                SecurityContextHolder.getContext().authentication = authentication
            } catch (_: io.jsonwebtoken.ExpiredJwtException) {
            } catch (_: JwtException) {
            } catch (_: Exception) {
            }
        }

        filterChain.doFilter(request, response)
    }
}
