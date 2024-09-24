package dsm.pick2024.domain.event.fcm.notice

interface SendMessageToNoticeEventPort {
    fun send(content: String)
}
