package dsm.pick2024.domain.admin.presentation.dto.request

import org.springframework.stereotype.Service

data class AdminLoginRequest (
    val adminId: String,
    val password: String
)
