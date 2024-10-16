package dsm.pick2024.global.error

import dsm.pick2024.global.error.exception.PickException
import dsm.pick2024.infrastructure.s3.exception.MaxUploadFileSizeException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.multipart.MaxUploadSizeExceededException
import javax.servlet.http.HttpServletResponse

@RestControllerAdvice
class GlobalExceptionHandler() {

    @ExceptionHandler(PickException::class)
    fun handlingPickException(e: PickException): ResponseEntity<ErrorResponse> {
        val code = e.errorCode
        return ResponseEntity(
            ErrorResponse(code.status, code.message),
            HttpStatus.valueOf(code.status)
        )
    }

    @ExceptionHandler(MaxUploadSizeExceededException::class)
    fun handleMaxSizeException(): ResponseEntity<ErrorResponse> {
        val error = MaxUploadFileSizeException
        return ResponseEntity(
            ErrorResponse(error.errorCode.status, error.errorCode.message),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(e: AccessDeniedException, response: HttpServletResponse) {
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied")
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun validatorExceptionHandler(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(
                400,
                e.bindingResult.allErrors[0].defaultMessage
            ),
            HttpStatus.BAD_REQUEST
        )
    }
}
