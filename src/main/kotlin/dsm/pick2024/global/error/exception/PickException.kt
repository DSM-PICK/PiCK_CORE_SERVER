package dsm.pick2024.global.error.exception

abstract class PickException(
    val errorCode: ErrorCode
) : RuntimeException()
