package dsm.pick2024.global.security.jwt

import dsm.pick2024.global.security.jwt.exception.AUTH_TOKEN_MISSING
import dsm.pick2024.global.security.jwt.path.SecurityPaths
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.AntPathMatcher
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtTokenFilter(
    private val jwtTokenProvider: JwtTokenProvider
) : OncePerRequestFilter() {

    private val pathMatcher = AntPathMatcher()

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val path = request.servletPath
        val shouldSkip = SecurityPaths.PERMITALLPATHS.any { permitPath ->
            pathMatcher.match(permitPath, path)
        }
        return shouldSkip
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // 토큰이 없어도 되는 경우는 shouldNotFilter에서 통과됨
        val token = jwtTokenProvider.resolveToken(request) ?: throw AUTH_TOKEN_MISSING

        val authentication = jwtTokenProvider.authentication(token)
        SecurityContextHolder.getContext().authentication = authentication

        filterChain.doFilter(request, response)
    }
}
