package dsm.pick2024.infrastructure.s3.exception

import dsm.pick2024.global.error.exception.ErrorCode
import dsm.pick2024.global.error.exception.PickException

object BadFileExtensionException : PickException(
    ErrorCode.BAD_FILE_EXTENSION
)
