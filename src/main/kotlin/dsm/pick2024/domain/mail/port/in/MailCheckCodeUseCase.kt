package dsm.pick2024.domain.mail.port.`in`

import dsm.pick2024.domain.mail.presentation.dto.request.CheckMailRequest

interface MailCheckCodeUseCase {
    fun execute(request: CheckMailRequest): Boolean
}
