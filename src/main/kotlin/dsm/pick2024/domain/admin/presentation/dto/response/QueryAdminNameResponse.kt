package dsm.pick2024.domain.admin.presentation.dto.response

data class QueryAdminNameResponse(
    val name: String,
    val grade: Int?,
    val classNum: Int?
)
