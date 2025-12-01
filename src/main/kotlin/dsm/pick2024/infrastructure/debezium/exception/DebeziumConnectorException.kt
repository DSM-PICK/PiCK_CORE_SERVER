package dsm.pick2024.infrastructure.debezium.exception

import dsm.pick2024.global.error.exception.ErrorCode
import dsm.pick2024.global.error.exception.PickException

object DebeziumConnectorException : PickException(
    ErrorCode.DEBEZIUM_CONNECTOR_ERROR
)
