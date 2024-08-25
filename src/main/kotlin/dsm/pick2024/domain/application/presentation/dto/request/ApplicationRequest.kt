package dsm.pick2024.domain.application.presentation.dto.request

import dsm.pick2024.domain.application.enums.ApplicationType

data class ApplicationRequest(
    val reason: String,
    val start: String,
    val end: String,
    val applicationType: ApplicationType
)
