package dsm.pick2024.domain.notice.service

import dsm.pick2024.domain.notice.exception.NoticeNotFoundException
import dsm.pick2024.domain.notice.port.`in`.ModifyNoticeUseCase
import dsm.pick2024.domain.notice.port.out.NoticeSavePort
import dsm.pick2024.domain.notice.port.out.QueryNoticePort
import dsm.pick2024.domain.notice.presentation.dto.request.ModifyNoticeRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ModifyNoticeService(
    private val queryNoticePort: QueryNoticePort,
    private val noticeSavePort: NoticeSavePort
) : ModifyNoticeUseCase {
    @Transactional
    override fun modifyNotice(request: ModifyNoticeRequest) {
        val notice =
            queryNoticePort.findById(request.id)
                ?: throw NoticeNotFoundException

        val update =
            notice.copy(
                title = request.title,
                content = request.content
            )

        noticeSavePort.save(update)
    }
}
