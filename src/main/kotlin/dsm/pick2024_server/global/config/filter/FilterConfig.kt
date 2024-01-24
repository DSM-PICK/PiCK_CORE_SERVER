package dsm.pick2024_server.global.config.filter

import com.fasterxml.jackson.databind.ObjectMapper
import dsm.pick2024_server.global.error.GlobalExceptionFilter
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.access.intercept.AuthorizationFilter

@Configuration
class FilterConfig (
    private val objectMapper: ObjectMapper
) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>(){
    override fun configure(builder: HttpSecurity) {
        builder.addFilterBefore(GlobalExceptionFilter(objectMapper), AuthorizationFilter::class.java)
    }
}
