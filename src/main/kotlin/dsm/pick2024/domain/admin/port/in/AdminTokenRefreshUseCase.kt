package dsm.pick2024.domain.admin.port.`in`

import dsm.pick2024.global.security.jwt.dto.TokenResponse

interface AdminTokenRefreshUseCase {
    fun adminTokenRefresh(token: String): TokenResponse
}
