package dsm.pick2024.global.config.swagger

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.filter.ForwardedHeaderFilter
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
@EnableWebMvc
class SwaggerConfig : WebMvcConfigurer {
    @Bean
    fun openApi(): OpenAPI {
        return OpenAPI()
            .info(
                Info().title("PiCK API")
                    .description("PiCK api 입니다.")
                    .version("v0.0.1")
            )
    }
}
