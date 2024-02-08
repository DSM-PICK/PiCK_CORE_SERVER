package dsm.pick2024.global.error.exception

enum class ErrorCode(
    val status: Int,
    val message: String,
) {
    FEIGN_BAD_REQUEST(400, "Feign Bad Request"),
    FEIGN_UNAUTHORIZED(401, "Feign UnAuthorized"),
    FEIGN_FORBIDDEN(403, "Feign Forbidden"),
    FEIGN_SERVER_ERROR(500, "Feign Server Error"),

    USER_NOT_FOUND(404, "User Not Found"),
    PASSWORD_MISS_MATCH(401, "Password Miss Match"),

    ADMIN_NOT_FOUND(404, "Admin Not Found"),

    SELF_STUDY_NOT_FOUND(404, "Self Study Not Found"),
    EXISTS_SELF_STUDY_TEACHER(409, "Exists Self Study Teacher"),

    ALREADY_APPLYING_EARLY_RETURN(409, "Already applying For Early Return Application"),

    ALREADY_APPLYING_MOVEMENT(409, "Already applying for movement"),

    ALREADY_APPLYING_PICNIC(409, "Already applying For Picnic Application"),

    INVALID_TOKEN(401, "Invalid Token"),
    EXPIRED_TOKEN(401, "Expired Token"),

    // Internal Server Error
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
}
