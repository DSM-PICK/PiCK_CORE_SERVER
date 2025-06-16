package dsm.pick2024.infrastructure.googleoauth.exception

import dsm.pick2024.global.error.exception.ErrorCode
import dsm.pick2024.global.error.exception.PickException

object GoogleOauthFailedException : PickException (
    ErrorCode.GOOGLE_OAUTH_SERVER_ERROR
)
