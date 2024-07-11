package dsm.pick2024.domain.bug.presentation.dto.request

import dsm.pick2024.domain.bug.domain.Model

data class BugRequest(
    val title: String,
    val content: String,
    val fileName: List<String>?,
    val model: Model
)
