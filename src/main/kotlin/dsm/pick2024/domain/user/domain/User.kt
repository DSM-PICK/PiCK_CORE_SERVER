package dsm.pick2024.domain.user.domain

import java.util.UUID

data class User(
    val id: UUID? = null,
    val name: String
)
