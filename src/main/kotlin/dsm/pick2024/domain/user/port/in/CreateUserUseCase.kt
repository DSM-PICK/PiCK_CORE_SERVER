package dsm.pick2024.domain.user.port.`in`

import dsm.pick2024.domain.user.presentation.dto.request.UserSignUpRequest
import java.util.UUID

interface CreateUserUseCase {
    fun createUser (request: UserSignUpRequest): UUID
}
