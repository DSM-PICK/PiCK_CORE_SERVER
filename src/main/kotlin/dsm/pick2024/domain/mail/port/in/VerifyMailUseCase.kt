package dsm.pick2024.domain.mail.port.`in`

import dsm.pick2024.domain.mail.presentation.dto.request.VerifyMailRequest

interface VerifyMailUseCase {
    fun execute(request: VerifyMailRequest): Boolean
}
