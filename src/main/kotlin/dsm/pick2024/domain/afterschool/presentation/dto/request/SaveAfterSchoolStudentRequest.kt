package dsm.pick2024.domain.afterschool.presentation.dto.request

data class SaveAfterSchoolStudentRequest(
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val name: String
)
