package dsm.pick2024.infrastructure.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import dsm.pick2024.domain.fcm.dto.request.FcmRequest
import dsm.pick2024.domain.fcm.port.out.FcmSendPort
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

@Component
class KafkaNotificationListener(
    private val objectMapper: ObjectMapper,
    private val fcmSendPort: FcmSendPort
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @KafkaListener(groupId = GROUP_ID, topics = [TOPIC])
    fun sendNotification(
        record: ConsumerRecord<String, String>,
        acknowledgment: Acknowledgment
    ) {
        try {
            val payload = extractPayload(record.value())
            val request: FcmRequest = objectMapper.readValue(payload)

            fcmSendPort.send(request)
            acknowledgment.acknowledge()
        } catch (e: Exception) {
            logger.error("Failed to process NOTIFICATION message. rawValue={}", record.value(), e)
        }
    }

    private fun extractPayload(raw: String): String {
        val node = objectMapper.readTree(raw)

        return when {
            node.isTextual -> node.asText()
            node.has(PAYLOAD_FIELD) -> {
                val payloadNode = node.get(PAYLOAD_FIELD)
                if (payloadNode.isTextual) payloadNode.asText() else payloadNode.toString()
            }
            else -> raw
        }
    }

    private companion object {
        const val GROUP_ID = "notification-service"
        const val TOPIC = "NOTIFICATION"
        const val PAYLOAD_FIELD = "payload"
    }
}
