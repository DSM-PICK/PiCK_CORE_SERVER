package dsm.pick2024.domain.user.presentation.dto.request

import dsm.pick2024.domain.user.entity.enums.Role

data class UserSignUpRequest (
    val accountId : String,
    val password : String,
    val name : String,
    val grade : Int,
    val classNum : Int,
    val num : Int,
    val role : Role
){
}
