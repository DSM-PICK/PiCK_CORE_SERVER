package dsm.pick2024.domain.bug.presentation.dto.request

data class BugRequest(
    val title: String,
    val content: String,
    val fileName: String?
)
