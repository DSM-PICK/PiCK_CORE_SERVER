package dsm.pick2024.global.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import dsm.pick2024.global.config.filter.FilterConfig
import dsm.pick2024.global.security.jwt.JwtTokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
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
        http
            .csrf()
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
            .antMatchers("/admin/login", "/user/login").permitAll()
            .anyRequest().permitAll()
//            .antMatchers(
//                HttpMethod.POST,
//                "/after/**",
//                "/meal",
//                "/notice",
//                "/schedule/create",
//                "/self-study/register",
//                "/timetable",
//                "/weekend-meal/saveAll",
//                "/status/saveAll",
//                "/schedule/**",
//            ).hasRole("SCH")
//            .antMatchers(
//                HttpMethod.GET,
//                "/admin/**",
//                "/after/**",
//                "/application/reason/all",
//                "/application/status",
//                "/application/floor",
//                "/application/grade",
//                "/application/all",
//                "/story/**",
//                "/class-room/floor",
//                "/class-room/grade",
//                "/early-return/grade",
//                "/early-return/floor",
//                "/early-return/reason/ok-all",
//                "/early-return/ok",
//                "/early-return/all",
//                "/self-study/month",
//                "/self-study/date",
//                "/self-study/admin",
//                "/weekend-meal/all",
//                "/weekend-meal/quit",
//                "/weekend-meal/hey",
//                "/status/**",
//                "/user/all",
//                "/status/grade",
//            ).hasRole(Role.SCH.name)
//            .antMatchers(
//                HttpMethod.PATCH,
//                "/application/**",
//                "/early-return/**",
//                "/notice/modify",
//                "/status/change",
//                "/weekend-meal/status",
//                "/schedule/modify",
//                "/after/change",
//                "/class-room/status",
//                "/class",
//            ).hasRole("SCH")
//            .antMatchers(
//                HttpMethod.DELETE,
//                "/after/**",
//                "/notice/delete/**",
//                "/schedule/delete/**",
//                "/after/delete",
//            )
//            .hasRole("SCH")
//            .antMatchers(
//                HttpMethod.POST,
//                "/application",
//                "/class-room/move",
//                "/early-return/create",
//            ).hasRole("STU")
//            .antMatchers(
//                HttpMethod.PATCH,
//                "/application/status",
//                "/weekend-meal/my-status",
//            ).hasRole("STU")
//            .antMatchers(
//                HttpMethod.GET,
//                "/user/simple",
//                "/user/details",
//                "/application/my",
//                "/application/simple",
//                "/class-room/move",
//                "/early-return/my",
//                "meal/date",
//                "/timetable/**",
//                "/weekend-meal/my",
//                "/main",
//            ).hasRole("STU")
//            .antMatchers(
//                HttpMethod.DELETE,
//                "/class-room/return",
//            ).hasRole("STU")
//            .antMatchers(
//                HttpMethod.GET,
//                "/application/non-return",
//            ).hasRole("SCH")
//            .antMatchers(
//                HttpMethod.GET,
//                "/weekend-meal/excel",
//            ).hasRole("SCH")

        http
            .apply(FilterConfig(objectMapper, jwtTokenProvider))

        return http.build()
    }

    @Bean
    protected fun passwordEncoder() = BCryptPasswordEncoder()
}
