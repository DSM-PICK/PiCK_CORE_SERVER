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
                "/after",
                "/meal",
                "/notice/",
                "/schedule/**",
                "/self-study/",
                "/timetable/"
            ).hasRole(Role.SCH.toString())
            .antMatchers(
                HttpMethod.PUT,
                "/after/**"
            ).hasRole(Role.SCH.toString())
            .antMatchers(
                HttpMethod.GET,
                "/admin/**",
                "/after/**",
                "/application/reason/all",
                "/application/non-return",
                "/application/status",
                "/story/**",
                "/class-room/floor",
                "/class-room/grade",
                "/early-return/grade",
                "/early-return/floor",
                "/early-return/reason/ok-all",
                "/schedule/modify",
                "/self-study/month",
                "/self-study/date",
                "/self-study/admin",
                "/weekend-meal/all",
                "/weekend-meal/quit",
                "/weekend-meal/excel"
            ).hasRole(Role.SCH.toString())
            .antMatchers(
                HttpMethod.PATCH,
                "/application/**",
                "/early-return/**",
                "/notice/**"
            ).hasRole(Role.SCH.toString())
            .antMatchers(
                HttpMethod.DELETE,
                "/after/**",
                "/notice/delete/",
                "/schedule/delete/"
            )
            .hasRole(Role.SCH.toString())
            .antMatchers(
                HttpMethod.POST,
                "/application/",
                "/class-room/move",
                "/early-return/create"
            ).hasRole(Role.STU.toString())
            .antMatchers(
                HttpMethod.PATCH,
                "/application/status",
                "/application/change/**",
                "/weekend-meal/status"
            ).hasRole(Role.STU.toString())
            .antMatchers(
                HttpMethod.GET,
                "/application/my",
                "/application/simple",
                "class-room/move",
                "/early-return/my",
                "/meal/date",
                "/timetable/**",
                "/user/simple",
                "/weekend-meal/my"
            ).hasRole(Role.STU.toString())
            .antMatchers(
                HttpMethod.DELETE,
                "/class-room/return"
            ).hasRole(Role.STU.toString())
            .antMatchers(
            ).permitAll()

        http
            .apply(FilterConfig(objectMapper, jwtTokenProvider))

        return http.build()
    }

    @Bean
    protected fun passwordEncoder() = BCryptPasswordEncoder()
}
