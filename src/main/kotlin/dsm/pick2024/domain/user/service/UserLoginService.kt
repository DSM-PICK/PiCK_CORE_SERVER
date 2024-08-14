package dsm.pick2024.domain.user.service

import dsm.pick2024.domain.user.domain.User
import dsm.pick2024.domain.user.entity.enums.Role
import dsm.pick2024.domain.user.exception.PasswordMissMatchException
import dsm.pick2024.domain.user.exception.UserNotFoundException
import dsm.pick2024.domain.user.port.`in`.LoginUseCase
import dsm.pick2024.domain.user.port.out.ExistsUserPort
import dsm.pick2024.domain.user.port.out.QueryUserPort
import dsm.pick2024.domain.user.port.out.UserSavePort
import dsm.pick2024.domain.user.presentation.dto.request.UserLoginRequest
import dsm.pick2024.global.security.jwt.JwtTokenProvider
import dsm.pick2024.global.security.jwt.dto.TokenResponse
import dsm.pick2024.infrastructure.feign.client.XquareFeignClient
import dsm.pick2024.infrastructure.feign.client.dto.request.XquareRequest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserLoginService(
    private val passwordEncoder: PasswordEncoder,
    private val queryUserPort: QueryUserPort,
    private val jwtTokenProvider: JwtTokenProvider,
    private val xquareFeignClient: XquareFeignClient,
    private val existsUserPort: ExistsUserPort,
    private val userSavePort: UserSavePort
) : LoginUseCase {

    @Transactional
    override fun login(userLoginRequest: UserLoginRequest): TokenResponse {
        val accountId = userLoginRequest.accountId

        val user = if (!existsUserPort.existsByAccountId(accountId)) {
            registerUser(userLoginRequest)
        } else {
            authenticateUser(userLoginRequest)
        }

        return generateToken(user.accountId)
    }

    private fun registerUser(userLoginRequest: UserLoginRequest): User {
        val xquareUser = xquareFeignClient.xquareUser(
            XquareRequest(userLoginRequest.accountId, userLoginRequest.password)
        )

        val newUser = User(
            id = xquareUser.id,
            accountId = xquareUser.accountId,
            password = xquareUser.password,
            name = xquareUser.name,
            grade = xquareUser.grade,
            classNum = xquareUser.classNum,
            num = xquareUser.num,
            birthDay = xquareUser.birthDay,
            role = xquareUser.userRole,
            profile = xquareUser.profileImageUrl,
            xquareId = xquareUser.id
        )

        userSavePort.save(newUser)

        return newUser
    }

    private fun authenticateUser(userLoginRequest: UserLoginRequest): User {
        val existingUser = queryUserPort.findByAccountId(userLoginRequest.accountId)
            ?: throw UserNotFoundException

        if (!passwordEncoder.matches(userLoginRequest.password, existingUser.password)) {
            throw PasswordMissMatchException
        }

        return existingUser
    }

    private fun generateToken(accountId: String): TokenResponse {
        return jwtTokenProvider.generateToken(accountId, Role.STU.toString())
    }

}
