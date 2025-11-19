package dsm.pick2024.infrastructure.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import dsm.pick2024.domain.fcm.dto.request.FcmRequest
import dsm.pick2024.domain.fcm.port.out.FcmSendPort
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

@Component
class KafkaNotificationListener(
    private val objectMapper: ObjectMapper,
    private val fcmSendPort: FcmSendPort
) {

    @KafkaListener(groupId = "notification-service", topics = ["NOTIFICATION"])
    fun sendNotification(
        data: ConsumerRecord<String, String>,
        acknowledgment: Acknowledgment,
        consumer: Consumer<String, String>
    ) {
        val fcmSendRequest = objectMapper.readValue<FcmRequest>(toPayload(data))

        fcmSendPort.send(fcmSendRequest)

        acknowledgment.acknowledge()
    }

    private fun toPayload(data: ConsumerRecord<String, String>): String {
        val root = objectMapper.readTree(data.value())
        return root["payload"].asText()
    }
}
