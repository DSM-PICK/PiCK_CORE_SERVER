package dsm.pick2024.global.config.swagger

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class SwaggerConfig : WebMvcConfigurer {
    @Bean
    fun openApi(): OpenAPI {
        val apiKey = SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")

        val securityRequirement = SecurityRequirement()
            .addList("Bearer Token")

        return OpenAPI()
            .components(Components().addSecuritySchemes("Bearer Token", apiKey))
            .addSecurityItem(securityRequirement)
            .addServersItem(Server().url("/dsm-pick"))
            .info(
                Info().title("PiCK API")
                    .description("PiCK api 입니다.")
                    .version("v0.0.1")
            )
    }
}
