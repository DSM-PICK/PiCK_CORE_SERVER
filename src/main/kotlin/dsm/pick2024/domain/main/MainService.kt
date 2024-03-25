package dsm.pick2024.domain.main

import dsm.pick2024.domain.application.exception.ApplicationNotFoundException
import dsm.pick2024.domain.application.port.out.FindApplicationByNamePort
import dsm.pick2024.domain.application.presentation.dto.response.QuerySimpleMyApplicationResponse
import dsm.pick2024.domain.classroom.port.out.FindByUserIdPort
import dsm.pick2024.domain.classroom.presentation.dto.response.UserMoveClassroomResponse
import dsm.pick2024.domain.earlyreturn.exception.EarlyReturnApplicationNotFoundException
import dsm.pick2024.domain.earlyreturn.port.out.FindEarlyReturnByUserIdPort
import dsm.pick2024.domain.earlyreturn.presentation.dto.response.QuerySimpleMyEarlyResponse
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class MainService(
    private val userFacadeUseCase: UserFacadeUseCase,
    private val findApplicationByNamePort: FindApplicationByNamePort,
    private val findEarlyReturnByUserIdPort: FindEarlyReturnByUserIdPort,
    private val findByUserIdPort: FindByUserIdPort
) {
    @Transactional(readOnly = true)
    fun main(): String {
        val userId = userFacadeUseCase.currentUser().id!!

        return findApplication(userId)
            ?: findEarlyReturn(userId)
            ?: findClassroom(userId)
    }

    private fun findApplication(userId: UUID): String? {
        val application = findApplicationByNamePort.findByUserId(userId)
            ?: throw ApplicationNotFoundException

        return QuerySimpleMyApplicationResponse(
            userId = application.userId,
            startTime = application.startTime,
            username = application.username,
            endTime = application.endTime
        ).toString()
    }

    private fun findEarlyReturn(userId: UUID): String? {
        val earlyReturn = findEarlyReturnByUserIdPort.findByUserId(userId)
            ?: throw EarlyReturnApplicationNotFoundException

        return QuerySimpleMyEarlyResponse(
            userId = earlyReturn.userId,
            startTime = earlyReturn.startTime,
            username = earlyReturn.username
        ).toString()
    }

    private fun findClassroom(userId: UUID): String {
        val classroom = findByUserIdPort.findByUserId(userId)
            ?: throw RuntimeException()

        return UserMoveClassroomResponse(
            username = classroom.username,
            classroom = classroom.classroomName
        ).toString()
    }
}
