package dsm.pick2024.domain.application.domain

import dsm.pick2024.domain.application.enums.ApplicationType
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.global.annotation.Aggregate
import java.time.LocalDate
import java.util.UUID

@Aggregate
data class Application(
    val id: UUID? = null,
    val userId: UUID,
    val reason: String,
    val date: LocalDate,
    val start: String,
    val end: String,
    val userName: String,
    val teacherName: String? = null,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val status: Status,
    val applicationType: ApplicationType
)
