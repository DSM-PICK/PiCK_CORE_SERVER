package dsm.pick2024.global.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import dsm.pick2024.domain.user.entity.enums.Role
import dsm.pick2024.global.config.filter.FilterConfig
import dsm.pick2024.global.security.jwt.JwtTokenProvider
import dsm.pick2024.global.security.path.SecurityPaths
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.HttpStatusEntryPoint

@EnableWebSecurity
class SecurityConfig(
    private val objectMapper: ObjectMapper,
    private val jwtTokenProvider: JwtTokenProvider
) {
    @Bean
    protected fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf().disable()
            .formLogin().disable()
            .cors()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        http.authorizeRequests()
            .antMatchers(*SecurityPaths.PERMIT_ALL_ENDPOINTS).permitAll()
            .antMatchers(
                HttpMethod.GET,
                *SecurityPaths.STU_GET_ENDPOINTS
            ).hasRole(Role.STU.name)
            .antMatchers(
                HttpMethod.GET,
                *SecurityPaths.SCH_GET_ENDPOINTS,
                "/attendance/time/grade/**"
            ).hasRole(Role.SCH.name)
            .antMatchers(
                HttpMethod.GET,
                *SecurityPaths.GET_AUTHENTICATED
            ).authenticated()
            .antMatchers(
                HttpMethod.POST,
                *SecurityPaths.STU_POST_ENDPOINTS
            ).hasRole(Role.STU.name)
            .antMatchers(
                HttpMethod.POST,
                *SecurityPaths.SCH_POST_ENDPOINTS
            ).hasRole(Role.SCH.name)
            .antMatchers(
                HttpMethod.PATCH,
                *SecurityPaths.STU_PATCH_ENDPOINTS
            ).hasRole(Role.STU.name)
            .antMatchers(
                HttpMethod.PATCH,
                *SecurityPaths.SCH_PATCH_ENDPOINTS
            ).hasRole(Role.SCH.name)
            .antMatchers(
                HttpMethod.DELETE,
                *SecurityPaths.STU_DELETE_ENDPOINTS
            ).hasRole(Role.STU.name)
            .antMatchers(
                HttpMethod.DELETE,
                *SecurityPaths.SCH_DELETE_ENDPOINTS
            )
            .hasRole(Role.SCH.name)
            .anyRequest().denyAll()
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
