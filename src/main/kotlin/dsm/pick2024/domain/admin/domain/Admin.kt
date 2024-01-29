package dsm.pick2024.domain.admin.domain

import java.util.*

data class Admin(
    val id: UUID? = null,
    val name: String,
    val classRoom: String? = null,
    val password: String,
    val adminId: String
)
