package dsm.pick2024.domain.notice.service

import dsm.pick2024.domain.notice.port.`in`.DeleteNoticeUseCase
import dsm.pick2024.domain.notice.port.out.DeleteNoticePort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class DeleteNoticeService(
    private val deleteNoticePort: DeleteNoticePort
) : DeleteNoticeUseCase {

    @Transactional
    override fun deleteNotice(id: UUID) =
        deleteNoticePort.deleteById(id)
}
