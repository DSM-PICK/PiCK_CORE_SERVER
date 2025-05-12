package dsm.pick2024.domain.mail.port.`in`

import dsm.pick2024.domain.mail.presentation.dto.request.SendMailRequest

interface SendMailUseCase {
    fun execute(request: SendMailRequest)
}
