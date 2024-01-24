package dsm.pick2024_server.global.error.exception

abstract class PickException(
    val errorCode: ErrorCode
) : RuntimeException()
