package dsm.pick2024.domain.notice.service

import dsm.pick2024.domain.admin.port.`in`.AdminFacadeUseCase
import dsm.pick2024.domain.notice.domain.Notice
import dsm.pick2024.domain.notice.port.`in`.CreateNoticeUseCase
import dsm.pick2024.domain.notice.port.out.NoticeSavePort
import dsm.pick2024.domain.notice.presentation.dto.request.NoticeRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class CreateNoticeService(
    private val noticeSavePort: NoticeSavePort,
    private val adminFacadeUseCase: AdminFacadeUseCase
) : CreateNoticeUseCase {

    @Transactional
    override fun createNotice(request: NoticeRequest) {
        val admin = adminFacadeUseCase.currentUser()
        noticeSavePort.save(
            Notice(
                adminId = admin.id!!,
                teacher = admin.name,
                createAt = LocalDateTime.now(),
                title = request.title,
                content = request.content
            )
        )
    }
}
