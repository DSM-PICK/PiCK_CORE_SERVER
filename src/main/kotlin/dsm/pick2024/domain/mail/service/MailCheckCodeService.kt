package dsm.pick2024.domain.mail.service

import dsm.pick2024.domain.mail.port.`in`.MailCheckCodeUseCase
import dsm.pick2024.domain.mail.port.`in`.VerifyMailUseCase
import dsm.pick2024.domain.mail.presentation.dto.request.UserCheckCodeRequest
import org.springframework.stereotype.Service

@Service
class MailCheckCodeService(
    private val verifyMailUseCase: VerifyMailUseCase
) : MailCheckCodeUseCase {
    override fun execute(request: UserCheckCodeRequest): Boolean {
        return (verifyMailUseCase.verifyOnly(request.code, request.email))
    }
}
