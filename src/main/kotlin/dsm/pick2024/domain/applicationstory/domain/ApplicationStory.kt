package dsm.pick2024.domain.applicationstory.domain

import dsm.pick2024.domain.applicationstory.enums.Type
import dsm.pick2024.global.annotation.Aggregate
import java.time.LocalDate
import java.util.UUID

@Aggregate
data class ApplicationStory(
    val id: UUID = UUID(0,0),
    val reason: String,
    val userId: UUID,
    val start: String,
    val end: String? = null,
    val userName: String,
    val returnTeacherName: String? = null,
    val date: LocalDate,
    val type: Type
) {
    fun updateReturnTeacher(returnTeacherName: String?): ApplicationStory {
        return this.copy(returnTeacherName = returnTeacherName)
    }
}
