package dsm.pick2024.domain.mail.port.`in`

import dsm.pick2024.domain.user.presentation.dto.request.UserCheckCodeRequest

interface MailCheckCodeUseCase {
    fun execute(request: UserCheckCodeRequest): Boolean
}
