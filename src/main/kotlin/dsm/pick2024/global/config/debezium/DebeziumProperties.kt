package dsm.pick2024.global.config.debezium

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "debezium")
data class DebeziumProperties(
    val connectUrl: String,
    val connector: ConnectorConfig,
    val database: DatabaseConfig,
    val schema: SchemaConfig,
    val outbox: OutboxConfig
) {
    data class ConnectorConfig(
        val name: String,
        val connectorClass: String = "io.debezium.connector.mysql.MySqlConnector",
        val tasksMax: Int = 1,
        val timePrecisionMode: String = "connect"
    )

    data class DatabaseConfig(
        val hostname: String,
        val port: Int,
        val user: String,
        val password: String,
        val serverId: String,
        val serverName: String,
        val includeList: String,
        val serverTimeZone: String = "Asia/Seoul"
    )

    data class SchemaConfig(
        val kafkaBootstrapServers: String,
        val historyTopic: String,
        val replicationFactor: Int,
        val includeSchemaChanges: Boolean
    )

    data class OutboxConfig(
        val tableIncludeList: String,
        val topicPrefix: String,
        val tableName: String,
        val fieldEventId: String,
        val fieldEventKey: String,
        val fieldEventType: String,
        val fieldPayload: String,
        val routeByField: String,
        val routeTopicReplacement: String = "\${routedByValue}"
    )

    fun toConnectorConfigMap(): Map<String, Any> {
        return mapOf(
            "connector.class" to connector.connectorClass,
            "tasks.max" to connector.tasksMax.toString(),

            "database.hostname" to database.hostname,
            "database.port" to database.port.toString(),
            "database.user" to database.user,
            "database.password" to database.password,
            "database.server.id" to database.serverId,
            "database.server.name" to database.serverName,
            "database.include.list" to database.includeList,

            "table.include.list" to outbox.tableIncludeList,
            "topic.prefix" to outbox.topicPrefix,

            "database.history.kafka.bootstrap.servers" to schema.kafkaBootstrapServers,
            "schema.history.internal.kafka.bootstrap.servers" to schema.kafkaBootstrapServers,
            "schema.history.internal.kafka.topic" to schema.historyTopic,
            "schema.history.internal.kafka.replication.factor" to schema.replicationFactor.toString(),
            "include.schema.changes" to schema.includeSchemaChanges.toString(),

            "tombstones.on.delete" to "false",
            "transforms" to "outbox",
            "transforms.outbox.type" to "io.debezium.transforms.outbox.EventRouter",
            "transforms.outbox.table.field.event.id" to outbox.fieldEventId,
            "transforms.outbox.table.field.event.payload" to outbox.fieldPayload,
            "transforms.outbox.table.field.event.type" to outbox.fieldEventType,
            "transforms.outbox.table.field.event.key" to outbox.fieldEventKey,
            "transforms.outbox.route.by.field" to outbox.routeByField,
            "transforms.outbox.route.topic.replacement" to outbox.routeTopicReplacement
        )
    }

    fun toConnectorRequest(): Map<String, Any> {
        return mapOf(
            "name" to connector.name,
            "config" to toConnectorConfigMap()
        )
    }
}
