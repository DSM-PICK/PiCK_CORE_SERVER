package dsm.pick2024.domain.admin.service

import dsm.pick2024.domain.admin.port.`in`.VerifySecretKeyUseCase
import dsm.pick2024.domain.admin.properties.AdminProperties
import dsm.pick2024.domain.admin.presentation.dto.request.SecretKeyRequest
import org.springframework.stereotype.Service

@Service
class VerifySecretKeyService(
    private val adminProperties: AdminProperties
) : VerifySecretKeyUseCase {
    override fun verifySecretKey(request: SecretKeyRequest): Boolean {
        return adminProperties.secretKey == request.secretKey
    }
}
