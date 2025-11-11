package dsm.pick2024.domain.admin.port.`in`

import dsm.pick2024.domain.admin.presentation.dto.request.SecretKeyRequest

interface VerifySecretKeyUseCase {
    fun verifySecretKey(request: SecretKeyRequest): Boolean
}
