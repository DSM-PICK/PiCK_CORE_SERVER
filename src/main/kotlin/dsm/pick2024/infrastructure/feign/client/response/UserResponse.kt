package dsm.pick2024.infrastructure.feign.client.response

data class UserResponse(
    val classNumber: String,
    val name: String,
    val part: String,
    val myClub: String
)
