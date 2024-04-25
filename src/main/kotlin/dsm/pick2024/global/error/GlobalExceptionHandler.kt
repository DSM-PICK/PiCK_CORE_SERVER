package dsm.pick2024.global.error

import dsm.pick2024.infrastructure.discord.DiscordComponet
import dsm.pick2024.global.error.exception.PickException
import org.springframework.context.MessageSource
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import java.io.Serializable

@RestControllerAdvice
class GlobalExceptionHandler(
    private val messageSource: MessageSource,
    private val discordComponet: DiscordComponet,
    private val environment: Environment
) {

    @ExceptionHandler(PickException::class)
    fun handlingPickException(e: PickException): ResponseEntity<ErrorResponse> {
        val code = e.errorCode
        return ResponseEntity(
            ErrorResponse(code.status, code.message),
            HttpStatus.valueOf(code.status)
        )
    }

    @ExceptionHandler(Exception::class)
    fun exception(e: PickException, request: WebRequest): ResponseEntity<Any>? {
        if (!listOf(environment.activeProfiles).contains<Serializable?>("local")) {
            discordComponet.sendDiscordAlarm(e, request)
            return ResponseEntity(
                ErrorResponse(e.errorCode.status, e.message),
                HttpStatus.valueOf(e.errorCode.status)
            )
        }
        return null
    }
}
