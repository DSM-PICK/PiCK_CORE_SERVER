package dsm.pick2024.global.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import dsm.pick2024.global.config.filter.FilterConfig
import dsm.pick2024.global.security.jwt.JwtTokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsUtils

@Configuration
class SecurityConfig(
    private val objectMapper: ObjectMapper,
    private val jwtTokenProvider: JwtTokenProvider
) {
    @Bean
    protected fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf()
            .disable()
            .cors()
            .and()
            .formLogin()
            .disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        http.authorizeRequests()
            .requestMatchers(CorsUtils::isCorsRequest)
            .permitAll()
            .antMatchers(HttpMethod.POST, "self-study/register")
            .hasRole("SCH")
            .antMatchers(HttpMethod.PATCH, "self-study/change")
            .hasRole("SCH")
            .antMatchers(HttpMethod.POST, "early-return/create")
            .hasRole("STU")
            .antMatchers(HttpMethod.PATCH, "early-return/*")
            .hasRole("SCH")
            .antMatchers(HttpMethod.POST, "application")
            .hasRole("STU")
            .antMatchers(HttpMethod.POST, "early-return/create")
            .hasRole("STU")
            .antMatchers(HttpMethod.PATCH, "application/status/*")
            .hasRole("SCH")
            .antMatchers(HttpMethod.GET, "application/floor")
            .hasRole("SCH")
            .antMatchers(HttpMethod.GET, "application/grade")
            .hasRole("SCH")
            .antMatchers(HttpMethod.PATCH, "early-return/status/*")
            .hasRole("SCH")
            .antMatchers(HttpMethod.GET, "story/*")
            .hasRole("SCH")

        http
            .apply(FilterConfig(objectMapper, jwtTokenProvider))

        return http.build()
    }

    @Bean
    protected fun passwordEncoder() = BCryptPasswordEncoder()
}
