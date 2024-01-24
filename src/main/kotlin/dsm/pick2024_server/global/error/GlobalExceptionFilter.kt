package dsm.pick2024_server.global.error

import com.fasterxml.jackson.databind.ObjectMapper
import dsm.pick2024_server.global.error.exception.PickException
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class GlobalExceptionFilter(
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: PickException) {
            val errorCode = e.errorCode
            val errorResponse = ErrorResponse(errorCode.status, errorCode.message)
            writeErrorResponse(response, errorCode.status, errorResponse)
        }
    }

    @Throws(IOException::class)
    private fun writeErrorResponse(response: HttpServletResponse, status:Int, errorResponse:
    ErrorResponse) {
        response.status = status
        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"
        objectMapper.writeValue(response.writer,errorResponse)
    }
}
