package dsm.pick2024.global.error

import com.fasterxml.jackson.databind.ObjectMapper
import dsm.pick2024.global.error.exception.PickException
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.web.util.ContentCachingRequestWrapper


class GlobalExceptionFilter(
    private val objectMapper: ObjectMapper,
    private val discordErrorNotifier: DiscordErrorNotifier
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val wrappedRequest = ContentCachingRequestWrapper(request)

        try {
            filterChain.doFilter(request, response)
        } catch (e: PickException) {

            val errorCode = e.errorCode
            val errorResponse = ErrorResponse(errorCode.status, errorCode.message)
            writeErrorResponse(response, errorCode.status, errorResponse)
            if (errorCode.status == 500) {
                val top = e.stackTrace.firstOrNull()
                val location = top?.let { "${it.className}.${it.methodName}(${it.fileName}:${it.lineNumber})" } ?: "Unknown"
                val exceptionMessage = "${e::class.simpleName}: ${e.message}"
                val requestBody = String(wrappedRequest.contentAsByteArray, Charsets.UTF_8)
                discordErrorNotifier.sendError(
                    url = wrappedRequest.requestURI,
                    message = exceptionMessage,
                    location = location,
                    requestBody = requestBody
                )
            }

        } catch (e: Exception) {
            // 예상치 못한 에러도 디스코드 전송
            val top = e.stackTrace.firstOrNull()
            val location = top?.let { "${it.className}.${it.methodName}(${it.fileName}:${it.lineNumber})" } ?: "Unknown"

            val exceptionMessage = "${e::class.simpleName}: ${e.message}"

            val requestBody = String(wrappedRequest.contentAsByteArray, Charsets.UTF_8)

            discordErrorNotifier.sendError(
                url = wrappedRequest.requestURI,
                message = exceptionMessage,
                location = location,
                requestBody = requestBody
            )
        }
    }

    @Throws(IOException::class)
    private fun writeErrorResponse(
        response: HttpServletResponse,
        status: Int,
        errorResponse: ErrorResponse
    ) {
        response.status = status
        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"
        objectMapper.writeValue(response.writer, errorResponse)
    }
}
