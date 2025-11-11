package dsm.pick2024.domain.admin.service

import dsm.pick2024.domain.admin.domain.Admin
import dsm.pick2024.domain.admin.exception.RegisteredClassAndGrade
import dsm.pick2024.domain.admin.exception.SecretKeyMissMatchException
import dsm.pick2024.domain.admin.port.`in`.AdminSignUpUseCase
import dsm.pick2024.domain.admin.port.out.AdminSavePort
import dsm.pick2024.domain.admin.port.out.ExistsByAdminIdPort
import dsm.pick2024.domain.admin.port.out.QueryAdminPort
import dsm.pick2024.domain.admin.presentation.dto.request.AdminSignUpRequest
import dsm.pick2024.domain.admin.properties.AdminProperties
import dsm.pick2024.domain.mail.port.`in`.VerifyMailUseCase
import dsm.pick2024.domain.user.entity.enums.Role
import dsm.pick2024.domain.user.exception.DuplicateUserException
import dsm.pick2024.global.security.jwt.JwtTokenProvider
import dsm.pick2024.global.security.jwt.dto.TokenResponse
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AdminSignUpService(
    private val passwordEncoder: PasswordEncoder,
    private val existsByAdminIdPort: ExistsByAdminIdPort,
    private val verifyMailUseCase: VerifyMailUseCase,
    private val jwtTokenProvider: JwtTokenProvider,
    private val adminSavePort: AdminSavePort,
    private val adminProperties: AdminProperties,
    private val queryAdminPort: QueryAdminPort
) : AdminSignUpUseCase {
    override fun execute(request: AdminSignUpRequest): TokenResponse {
        val encodedPassword = passwordEncoder.encode(request.password)

        checkDuplicateAccountId(request.accountId)

        checkSecretKey(request.secretKey)

        checkRegisteredGradeAndClass(request.grade, request.classNum)

        verifyMailUseCase.verifyAndConsume(request.code, request.accountId)

        val user = request.toEntity(encodedPassword)
        adminSavePort.save(user)

        return jwtTokenProvider.generateToken(request.accountId, Role.SCH.name)
    }

    private fun checkDuplicateAccountId(accountId: String) {
        if (existsByAdminIdPort.existsByAdminId(accountId)) {
            throw DuplicateUserException
        }
    }

    private fun checkSecretKey(key: String) {
        if (adminProperties.secretKey != key) {
            throw SecretKeyMissMatchException
        }
    }

    private fun checkRegisteredGradeAndClass(grade: Int, classNum: Int) {
        if (grade == 0 && classNum == 0) {
            return
        }
        if (queryAdminPort.isGradeAndClassRegistered(grade, classNum)) {
            throw RegisteredClassAndGrade
        }
    }

    private fun AdminSignUpRequest.toEntity(encodedPassword: String): Admin {
        return Admin(
            adminId = this.accountId,
            password = encodedPassword,
            name = this.name,
            grade = this.grade,
            classNum = this.classNum,
            role = Role.SCH,
            deviceToken = this.deviceToken
        )
    }
}
