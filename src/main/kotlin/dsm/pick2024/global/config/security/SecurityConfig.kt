package dsm.pick2024.global.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import dsm.pick2024.domain.user.entity.enums.Role
import dsm.pick2024.global.config.filter.FilterConfig
import dsm.pick2024.global.security.jwt.JwtTokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.web.cors.CorsUtils

@EnableWebSecurity
class SecurityConfig(
    private val objectMapper: ObjectMapper,
    private val jwtTokenProvider: JwtTokenProvider
) {
    @Bean
    protected fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf()
            .disable()
            .formLogin().and().cors()
            .disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        http.authorizeRequests()
            .requestMatchers(CorsUtils::isCorsRequest)
            .permitAll()
            .antMatchers(
                "/admin/login",
                "/admin/refresh",
                "/user/login",
                "/user/refresh",
                "/", "/swagger-ui/**",
                "/v3/api-docs/**",
                "/dsm-pick/swagger-ui/index.html",
                "/dsm-pick/swagger-ui/index.html/**",
                "/main"
            ).permitAll()
            .antMatchers(
                HttpMethod.POST,
                "/after/**",
                "/meal",
                "/notice",
                "/schedule/create",
                "/self-study/register",
                "/timetable",
                "/weekend-meal/saveAll",
                "/status/saveAll",
                "/schedule/**"
            ).hasRole(Role.SCH.name)
            .antMatchers(
                HttpMethod.GET,
                "/admin/**",
                "/after/**",
                "/application/reason/all",
                "/application/status",
                "/application/floor",
                "/application/grade",
                "/application/all",
                "/story/**",
                "/class-room/floor",
                "/class-room/grade",
                "/early-return/grade",
                "/early-return/floor",
                "/early-return/reason/ok-all",
                "/early-return/ok",
                "/early-return/all",
                "/self-study/month",
                "/self-study/date",
                "/self-study/admin",
                "/weekend-meal/all",
                "/weekend-meal/hey",
                "/status/**",
                "/user/all",
                "/status/grade",
                "/timetable/all"
            ).hasRole(Role.SCH.name)
            .antMatchers(
                HttpMethod.PATCH,
                "/application/**",
                "/early-return/**",
                "/notice/modify",
                "/status/change",
                "/weekend-meal/status",
                "/schedule/modify",
                "/after/change",
                "/class-room/status",
                "/class",
                "weekend-meal/period",
                "/timetable/change"
            ).hasRole(Role.SCH.name)
            .antMatchers(
                HttpMethod.DELETE,
                "/after/**",
                "/notice/delete/**",
                "/schedule/delete/**",
                "/after/delete"
            )
            .hasRole(Role.SCH.name)
            .antMatchers(
                HttpMethod.POST,
                "/application",
                "/class-room/move",
                "/early-return/create"
            ).hasRole(Role.STU.name)
            .antMatchers(
                HttpMethod.PATCH,
                "/application/status",
                "/weekend-meal/my-status",
                "/user/profile"
                "/notification/**"
            ).hasRole(Role.STU.name)
            .antMatchers(
                HttpMethod.GET,
                "/user/simple",
                "/user/details",
                "/application/my",
                "/application/simple",
                "/class-room/move",
                "/early-return/my",
                "meal/date",
                "/timetable/today",
                "/timetable/week",
                "/weekend-meal/my",
                "/notification/**"
            ).hasRole(Role.STU.name)
            .antMatchers(
                HttpMethod.DELETE,
                "/class-room/return"
            ).hasRole(Role.STU.name)
            .antMatchers(
                HttpMethod.GET,
                "/application/non-return"
            ).hasRole(Role.SCH.name)
            .antMatchers(
                HttpMethod.GET,
                "/weekend-meal/excel",
                "/weekend-meal/excel/grade"
            ).hasRole(Role.SCH.name)
            .anyRequest().authenticated()
//            .anyRequest().permitAll()
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))

        http
            .apply(FilterConfig(objectMapper, jwtTokenProvider))

        return http.build()
    }

    @Bean
    protected fun passwordEncoder() = BCryptPasswordEncoder()
}
