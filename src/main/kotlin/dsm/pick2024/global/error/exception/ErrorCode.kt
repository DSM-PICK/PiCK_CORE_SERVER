package dsm.pick2024.global.error.exception

enum class ErrorCode(
    val status: Int,
    val message: String
) {
    FEIGN_BAD_REQUEST(400, "Feign 잘못된 요청"),
    FILE_IS_EMPTY(400, "파일이 존재하지 않습니다"),
    BAD_FILE_EXTENSION(400, "잘못된 파일 확장자입니다"),
    MAX_UPLOAD_FILE(400, "최대 업로드 파일 크기를 초과했습니다"),
    INVALID_PERIOD(400, "유효하지 않은 기간입니다"),
    INVALID_TIME(400, "유효하지 않은 시간입니다"),
    INVALID_SUBJECT(400, "유효하지 않은 과목입니다"),
    MOVE_REQUIRED_ON_CLUB_DAY(400, "전공동아리 날에는 시작 위치가 필수입니다"),

    FEIGN_UNAUTHORIZED(401, "Feign 인증 실패"),
    PASSWORD_MISS_MATCH(401, "비밀번호가 일치하지 않습니다"),
    INVALID_TOKEN(401, "유효하지 않은 토큰입니다"),
    EXPIRED_TOKEN(401, "만료된 토큰입니다"),
    SECRET_KEY_MISS_MATCH(401, "비밀 키가 일치하지 않습니다"),
    EXPIRED_EMAIL_CODE(401, "만료된 이메일 인증 코드입니다"),
    EMAIL_CODE_MISMATCH(401, "이메일 인증 코드가 일치하지 않습니다"),
    AUTH_TOKEN_MISSING(401, "토큰이 존재하지 않습니다."),

    NOT_ADMIN(403, "관리자가 아닙니다"),
    NOT_STUDENT(403, "학생이 아닙니다"),
    FEIGN_FORBIDDEN(403, "Feign 접근 거부"),

    USER_NOT_FOUND(404, "존재하지 않는 학생입니다."),
    ADMIN_NOT_FOUND(404, "존재하지 않는 선생님입니다."),
    SELF_STUDY_NOT_FOUND(404, "자습 정보를 찾을 수 없습니다"),
    EARLY_RETURN_NOT_FOUND(404, "조기 귀가 신청을 찾을 수 없습니다"),
    APPLICATION_NOT_FOUND(404, "신청서를 찾을 수 없습니다"),
    STATUS_NOT_FOUND(404, "상태를 찾을 수 없습니다"),
    CLASSROOM_NOT_FOUND(404, "교실을 찾을 수 없습니다"),
    WEEKEND_MEAL_NOT_FOUND(404, "존재하지 않는 주말 급식입니다."),
    CLUB_NOT_FOUND(404, "동아리를 찾을 수 없습니다"),
    NOTICE_NOT_FOUND(404, "공지사항을 찾을 수 없습니다"),
    SCHEDULE_NOT_FOUND(404, "일정을 찾을 수 없습니다"),
    FLOOR_NOT_FOUND(404, "존재하지 않는 층입니다."),
    TEACHER_NOT_FOUND(404, "교사를 찾을 수 없습니다"),
    TIMETABLE_NOT_FOUND(404, "시간표를 찾을 수 없습니다"),

    EXISTS_SELF_STUDY_TEACHER(409, "자습 교사가 이미 존재합니다"),
    ALREADY_APPLYING_EARLY_RETURN(409, "이미 조기귀가를 신청했습니다."),
    ALREADY_APPLYING_PICNIC(409, "이미 외출을 신청했습니다."),
    ALREADY_APPLYING_MOVEMENT(409, "이미 이동 신청을 진행하였습니다."),
    DUPLICATE_USER(409, "중복된 사용자입니다"),
    REGISTERED_CLASS_AND_GRADE_AND_NUM(409, "이미 존재하는 학번입니다. 가까운 픽에 문의하세요"),
    REGISTERED_CLASS_AND_GRADE(409, "이미 담당 선생님이 존재하는 학년,반입니다. 가까운 픽에 문의하세요"),

    NOT_EXTENSION_XSL(415, "XSL 확장자가 아닙니다"),

    TOO_MANY_REQUEST(429, "너무 많은 요청입니다."),

    // Internal Server Error
    INTERNAL_SERVER_ERROR(500, "내부 서버 오류"),
    FEIGN_SERVER_ERROR(500, "Feign 서버 오류"),
    GOOGLE_OAUTH_SERVER_ERROR(500, "Google OAuth 서버 오류"),
    FCM_SERVER_ERROR(500, "FCM 서버 오류"),
    FCM_INITIALIZATION_ERROR(500, "FCM 초기화 오류"),
    DEBEZIUM_CONNECTOR_ERROR(500, "Debezium 커넥터 오류")
}
