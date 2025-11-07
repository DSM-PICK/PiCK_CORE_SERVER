package dsm.pick2024.global.config.swagger

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class SwaggerConfig : WebMvcConfigurer {
    @Bean
    fun openApi(): OpenAPI {
        return OpenAPI()
            .addServersItem(Server().url("/"))
            .info(
                Info().title("PiCK API")
                    .description("PiCK api 입니다.")
                    .version("v0.0.1")
            )
    }
}
