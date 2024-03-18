package dsm.pick2024.domain.notice.service

import dsm.pick2024.domain.notice.port.`in`.ModifyNoticeUseCase
import dsm.pick2024.domain.notice.port.out.FindByNoticeIdPort
import dsm.pick2024.domain.notice.port.out.NoticeSavePort
import dsm.pick2024.domain.notice.presentation.dto.request.ModifyNoticeRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ModifyNoticeService(
    private val findByNoticeIdPort: FindByNoticeIdPort,
    private val noticeSavePort: NoticeSavePort
) : ModifyNoticeUseCase {

    @Transactional
    override fun modifyNotice(request: ModifyNoticeRequest) {
        val notice = findByNoticeIdPort.findById(request.id)
            ?: throw RuntimeException()

        val update = notice.copy(
            title = request.title,
            content = request.content,
            forStudent = request.forStudent
        )

        noticeSavePort.save(update)
    }
}
