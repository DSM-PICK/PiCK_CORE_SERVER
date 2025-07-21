package dsm.pick2024.global.security.jwt

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtTokenFilter(
    private val jwtTokenProvider: JwtTokenProvider
) : OncePerRequestFilter() {

    private val excludedPaths = listOf("/user/signup","/admin/signup","/user/login","/admin/login")

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val path = request.requestURI

        if (excludedPaths.any { path.endsWith(it) }) {
            filterChain.doFilter(request, response)
            return
        }

        val token = jwtTokenProvider.resolveToken(request)

        token?.run {
            SecurityContextHolder.getContext().authentication = jwtTokenProvider.authentication(token)
        }

        filterChain.doFilter(request, response)
    }
}
