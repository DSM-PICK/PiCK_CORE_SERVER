package dsm.pick2024.domain.user.service

import dsm.pick2024.domain.attendance.domain.Attendance
import dsm.pick2024.domain.attendance.enums.AttendanceStatus
import dsm.pick2024.domain.attendance.port.out.SaveAttendancePort
import dsm.pick2024.domain.devicetoken.domain.UserDeviceToken
import dsm.pick2024.domain.devicetoken.port.out.SaveUserDeviceTokenPort
import dsm.pick2024.domain.mail.port.`in`.VerifyMailUseCase
import dsm.pick2024.domain.user.domain.User
import dsm.pick2024.domain.user.entity.enums.Role
import dsm.pick2024.domain.user.exception.DuplicateUserException
import dsm.pick2024.domain.user.exception.RegisteredClassAndGradeAndNum
import dsm.pick2024.domain.user.port.`in`.UserSignUpUseCase
import dsm.pick2024.domain.user.port.out.ExistsUserPort
import dsm.pick2024.domain.user.port.out.UserSavePort
import dsm.pick2024.domain.user.presentation.dto.request.UserSignUpRequest
import dsm.pick2024.domain.weekendmeal.domain.WeekendMeal
import dsm.pick2024.domain.weekendmeal.enums.Status
import dsm.pick2024.domain.weekendmeal.port.out.SaveWeekendMealPort
import dsm.pick2024.global.security.jwt.JwtTokenProvider
import dsm.pick2024.global.security.jwt.dto.TokenResponse
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
class UserSignUpService(
    private val savePort: UserSavePort,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider,
    private val existsUserPort: ExistsUserPort,
    private val verifyMailUseCase: VerifyMailUseCase,
    private val saveAttendancePort: SaveAttendancePort,
    private val saveWeekendMealPort: SaveWeekendMealPort,
    private val saveUserDeviceTokenPort: SaveUserDeviceTokenPort
) : UserSignUpUseCase {

    @Transactional
    override fun execute(request: UserSignUpRequest): TokenResponse {
        val encodedPassword = passwordEncoder.encode(request.password)

        if (existsUserPort.existsByAccountId(request.accountId)) {
            throw DuplicateUserException
        }

        checkRegisteredGradeAndClassNumAndNum(request.grade, request.classNum, request.num)
        verifyMailUseCase.verifyAndConsume(request.code, request.accountId)

        val user = request.toDomain(encodedPassword)
        val savedUser = savePort.save(user)

        request.deviceToken?.let { token ->
            saveUserDeviceTokenPort.save(
                UserDeviceToken(
                    id = UUID.randomUUID(),
                    userId = savedUser.id,
                    deviceToken = token,
                    os = request.os
                )
            )
        }

        setupInitialData(savedUser)

        return jwtTokenProvider.generateToken(savedUser.accountId, Role.STU.name)
    }

    private fun setupInitialData(user: User) {
        saveAttendancePort.save(
            Attendance(
                userId = user.id,
                grade = user.grade,
                classNum = user.classNum,
                num = user.num,
                userName = user.name,
                period6 = AttendanceStatus.ATTENDANCE,
                period7 = AttendanceStatus.ATTENDANCE,
                period8 = AttendanceStatus.ATTENDANCE,
                period9 = AttendanceStatus.ATTENDANCE,
                period10 = AttendanceStatus.ATTENDANCE
            )
        )
        saveWeekendMealPort.save(
            WeekendMeal(
                userId = user.id,
                userName = user.name,
                grade = user.grade,
                classNum = user.classNum,
                num = user.num,
                status = Status.NO
            )
        )
    }

    private fun checkRegisteredGradeAndClassNumAndNum(grade: Int, classNum: Int, num: Int) {
        if (existsUserPort.existsByGradeAndClassNumAndNum(grade, classNum, num)) {
            throw RegisteredClassAndGradeAndNum
        }
    }

    private fun UserSignUpRequest.toDomain(encodedPassword: String): User {
        return User(
            accountId = this.accountId,
            password = encodedPassword,
            name = this.name,
            grade = this.grade,
            classNum = this.classNum,
            num = this.num,
            profile = null,
            role = Role.STU
        )
    }
}
