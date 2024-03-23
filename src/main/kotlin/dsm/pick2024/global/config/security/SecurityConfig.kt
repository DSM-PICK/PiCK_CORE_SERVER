package dsm.pick2024.global.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import dsm.pick2024.domain.user.entity.enums.Role
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
            .antMatchers(
                HttpMethod.POST,
                "/dsm-pick/after/**",
                "/dsm-pick/meal",
                "/dsm-pick/notice/",
                "/dsm-pick/schedule/create",
                "/dsm-pick/self-study/",
                "/dsm-pick/timetable/",
                "/dsm-pick/weekend-meal/status"
            ).hasRole(Role.SCH.toString())
            .antMatchers(
                HttpMethod.PUT,
                "/dsm-pick/after/**"
            ).hasRole(Role.SCH.toString())
            .antMatchers(
                HttpMethod.GET,
                "/dsm-pick/admin/**",
                "/dsm-pick/after/**",
                "/dsm-pick/application/reason/all",
                "/dsm-pick/application/non-return",
                "/dsm-pick/application/status",
                "/dsm-pick/story/**",
                "/dsm-pick/class-room/floor",
                "/dsm-pick/class-room/grade",
                "/dsm-pick/early-return/grade",
                "/dsm-pick/early-return/floor",
                "/dsm-pick/early-return/reason/ok-all",
                "/dsm-pick/schedule/modify",
                "/dsm-pick/self-study/month",
                "/dsm-pick/self-study/date",
                "/dsm-pick/self-study/admin",
                "/dsm-pick/weekend-meal/all",
                "/dsm-pick/weekend-meal/quit",
                "/dsm-pick/weekend-meal/excel"
            ).hasRole(Role.SCH.toString())
            .antMatchers(
                HttpMethod.PATCH,
                "/dsm-pick/application/**",
                "/dsm-pick/early-return/**",
                "/dsm-pick/notice/**",
                "/dsm-pick/status/change"
            ).hasRole(Role.SCH.toString())
            .antMatchers(
                HttpMethod.DELETE,
                "/dsm-pick/after/**",
                "/dsm-pick/notice/delete/",
                "/dsm-pick/schedule/delete/"
            )
            .hasRole(Role.SCH.toString())
            .antMatchers(
                HttpMethod.POST,
                "/dsm-pick/application/",
                "/dsm-pick/class-room/move",
                "/dsm-pick/early-return/create"
            ).hasRole(Role.STU.toString())
            .antMatchers(
                HttpMethod.PATCH,
                "/dsm-pick/application/status",
                "/dsm-pick/application/change/**",
                "/dsm-pick/weekend-meal/my-status"
            ).hasRole(Role.STU.toString())
            .antMatchers(
                HttpMethod.GET,
                "/dsm-pick/application/my",
                "/dsm-pick/application/simple",
                "/dsm-pick/class-room/move",
                "/dsm-pick/early-return/my",
                "/dsm-pick/meal/date",
                "/dsm-pick/timetable/**",
                "/dsm-pick/user/simple",
                "/dsm-pick/weekend-meal/my"
            ).hasRole(Role.STU.toString())
            .antMatchers(
                HttpMethod.DELETE,
                "/dsm-pick/class-room/return"
            ).hasRole(Role.STU.toString())

        http
            .apply(FilterConfig(objectMapper, jwtTokenProvider))

        return http.build()
    }

    @Bean
    protected fun passwordEncoder() = BCryptPasswordEncoder()
}
