package dsm.pick2024.domain.event.notice

interface SendMessageToNoticeEventPort {
    fun send(content: String)
}
