package dsm.pick2024.domain.fcm.port.out

interface FcmSendPort {
    fun sendAll(deviceTokens: List<String>, title: String, body: String)
    fun send(deviceToken: String, title: String, body: String)
}
