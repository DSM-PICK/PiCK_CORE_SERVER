package dsm.pick2024.infrastructure.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import dsm.pick2024.domain.fcm.dto.request.FcmRequest
import dsm.pick2024.domain.fcm.port.out.FcmSendPort
import org.apache.kafka.clients.consumer.Consumer
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

    val logger = LoggerFactory.getLogger(javaClass)

    @KafkaListener(groupId = "notification-service", topics = ["NOTIFICATION"])
    fun sendNotification(
        data: ConsumerRecord<String, String>,
        acknowledgment: Acknowledgment,
        consumer: Consumer<String, String>
    ) {
        logger.info(
            "Kafka record topic={}, partition={}, offset={}, key={}, value={} ",
            data.topic(),
            data.partition(),
            data.offset(),
            data.key(),
            data.value()
        )
        try {
            val fcmSendRequest = objectMapper.readValue<FcmRequest>(toPayload(data))
            logger.info("FCM request={}", fcmSendRequest)
            fcmSendPort.send(fcmSendRequest)

            acknowledgment.acknowledge()
        } catch (e: Exception) {
            // 파싱/전송 중 실패 시 원문을 함께 남겨서 디버깅 가능하게 함
            logger.error("Failed to process NOTIFICATION message. rawValue={}", data.value(), e)
            // 필요하면 여기서 DLQ로 보내거나, 재처리 정책에 맞게 ack 여부를 결정하세요.
            // 지금은 ack 하지 않아 재시도(또는 컨테이너 설정에 따른 처리)가 가능하게 둡니다.
        }
    }

    private fun toPayload(data: ConsumerRecord<String, String>): String {
        val raw = data.value()
        val node = objectMapper.readTree(raw)

        // Debezium/Kafka UI에서 보이는 것처럼 값 자체가 JSON 문자열(이중 인코딩)로 오는 경우
        // 예) "{\"body\":...}"  -> readTree 결과가 TextNode가 되고, asText()가 내부 JSON을 복원해줌
        if (node.isTextual) {
            return node.asText()
        }

        // wrapper 형태({"payload": ...})면 payload를 우선 사용
        val payloadNode = node.get("payload") ?: return raw

        // payload가 문자열이면 그대로 풀어서 반환, 객체면 JSON 문자열로 반환
        return if (payloadNode.isTextual) payloadNode.asText() else payloadNode.toString()
    }
}
