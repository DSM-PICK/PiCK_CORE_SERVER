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
            ).hasRole(Role.SCH.toString())
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
            ).hasRole(Role.SCH.toString())
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
            ).hasRole(Role.SCH.toString())
            .antMatchers(
                HttpMethod.DELETE,
                "/dsm-pick/after/**",
                "/dsm-pick/notice/delete/**",
                "/dsm-pick/schedule/delete/**",
                "/dsm-pick/after/delete"
            )
            .hasRole(Role.SCH.toString())
            .antMatchers(
                HttpMethod.POST,
                "/dsm-pick/application",
                "/dsm-pick/class-room/move",
                "/dsm-pick/early-return/create"
            ).hasRole(Role.STU.toString())
            .antMatchers(
                HttpMethod.PATCH,
                "/dsm-pick/application/status",
                "/dsm-pick/weekend-meal/my-status"
            ).hasRole(Role.STU.toString())
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
            ).hasRole(Role.STU.toString())
            .antMatchers(
                HttpMethod.DELETE,
                "/dsm-pick/class-room/return"
            ).hasRole(Role.STU.toString())
            .antMatchers(
                HttpMethod.GET,
                "/dsm-pick/application/non-return"
            ).hasAnyRole(Role.SCH.toString(), Role.SEC.toString())
            .antMatchers(
                HttpMethod.GET,
                "/dsm-pick/weekend-meal/excel"
            ).hasAnyRole(Role.SCH.toString(), Role.COOK.toString())
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
