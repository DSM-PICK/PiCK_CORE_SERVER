package dsm.pick2024_server.global.error.exception

enum class ErrorCode(
    val status: Int,
    val message: String
) {
    FEIGN_BAD_REQUEST(400, "Feign Bad Request"),
    FEIGN_UNAUTHORIZED(401, "Feign UnAuthorized"),
    FEIGN_FORBIDDEN(403, "Feign Forbidden"),
    FEIGN_SERVER_ERROR(500, "Feign Server Error"),

    USER_NOT_FOUND(404, "User Not Found"),

    INVALID_TOKEN(401, "Invalid Token"),
    EXPIRED_TOKEN(401, "Expired Token"),

    // Internal Server Error
    INTERNAL_SERVER_ERROR(500, "Internal Server Error")
}
