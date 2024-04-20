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

    companion object {
        private const val STU = "STU"
        private const val SCH = "SCH"
        private const val SEC = "SEC"
        private const val COOK = "COOK"
    }

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
            .antMatchers("/dsm-pick/admin/login", "/dsm-pick/user/login").permitAll()
            .antMatchers(
                HttpMethod.POST,
                "/dsm-pick/after/**",
                "/dsm-pick/meal",
                "/dsm-pick/notice",
                "/dsm-pick/schedule/create",
                "/dsm-pick/self-study/register",
                "/dsm-pick/timetable",
                "/dsm-pick/weekend-meal/saveAll",
                "/dsm-pick/status/saveAll",
                "/dsm-pick/schedule/**"
            ).hasRole(SCH)
            .antMatchers(
                HttpMethod.GET,
                "/dsm-pick/admin/**",
                "/dsm-pick/after/**",
                "/dsm-pick/application/reason/all",
                "/dsm-pick/application/status",
                "/dsm-pick/application/floor",
                "/dsm-pick/application/grade",
                "/dsm-pick/application/all",
                "/dsm-pick/story/**",
                "/dsm-pick/class-room/floor",
                "/dsm-pick/class-room/grade",
                "/dsm-pick/early-return/grade",
                "/dsm-pick/early-return/floor",
                "/dsm-pick/early-return/reason/ok-all",
                "/dsm-pick/early-return/ok",
                "/dsm-pick/early-return/all",
                "/dsm-pick/self-study/month",
                "/dsm-pick/self-study/date",
                "/dsm-pick/self-study/admin",
                "/dsm-pick/weekend-meal/all",
                "/dsm-pick/weekend-meal/quit",
                "/dsm-pick/weekend-meal/hey",
                "/dsm-pick/status/**",
                "/dsm-pick/user/all",
                "/dsm-pick/status/grade"
            ).hasRole(SCH)
            .antMatchers(
                HttpMethod.PATCH,
                "/dsm-pick/application/**",
                "/dsm-pick/early-return/**",
                "/dsm-pick/notice/modify",
                "/dsm-pick/status/change",
                "/dsm-pick/weekend-meal/status",
                "/dsm-pick/schedule/modify",
                "/dsm-pick/after/change",
                "/dsm-pick/class-room/status",
                "/dsm-pick/class"
            ).hasRole(SCH)
            .antMatchers(
                HttpMethod.DELETE,
                "/dsm-pick/after/**",
                "/dsm-pick/notice/delete/**",
                "/dsm-pick/schedule/delete/**",
                "/dsm-pick/after/delete"
            )
            .hasRole(SCH)
            .antMatchers(
                HttpMethod.POST,
                "/dsm-pick/application",
                "/dsm-pick/class-room/move",
                "/dsm-pick/early-return/create"
            ).hasRole(STU)
            .antMatchers(
                HttpMethod.PATCH,
                "/dsm-pick/application/status",
                "/dsm-pick/weekend-meal/my-status"
            ).hasRole(STU)
            .antMatchers(
                HttpMethod.GET,
                "dsm-pick/user/simple",
                "dsm-pick/user/details",
                "/dsm-pick/application/my",
                "/dsm-pick/application/simple",
                "/dsm-pick/class-room/move",
                "/dsm-pick/early-return/my",
                "/dsm-pick/meal/date",
                "/dsm-pick/timetable/**",
                "/dsm-pick/weekend-meal/my",
                "/dsm-pick/main"
            ).hasRole(STU)
            .antMatchers(
                HttpMethod.DELETE,
                "/dsm-pick/class-room/return"
            ).hasRole(STU)
            .antMatchers(
                HttpMethod.GET,
                "/dsm-pick/application/non-return"
            ).hasAnyRole(SCH, SEC)
            .antMatchers(
                HttpMethod.GET,
                "/dsm-pick/weekend-meal/excel"
            ).hasAnyRole(SCH, COOK)
            .anyRequest().permitAll()

        http
            .apply(FilterConfig(objectMapper, jwtTokenProvider))

        return http.build()
    }

    @Bean
    protected fun passwordEncoder() = BCryptPasswordEncoder()
}
