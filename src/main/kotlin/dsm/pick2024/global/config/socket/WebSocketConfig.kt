package dsm.pick2024.global.config.socket

import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@Configuration
@EnableWebSocket
class WebSocketConfig(
    private val mainWebSocketHandler: MainWebSocketHandler
) : WebSocketConfigurer {
    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(mainWebSocketHandler, "/main").setAllowedOrigins("*")
    }
}
