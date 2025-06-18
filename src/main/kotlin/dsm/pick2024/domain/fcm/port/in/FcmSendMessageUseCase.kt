package dsm.pick2024.domain.fcm.port.`in`

interface FcmSendMessageUseCase {
    fun execute(deviceTokens: List<String>, title: String, body: String)
}
