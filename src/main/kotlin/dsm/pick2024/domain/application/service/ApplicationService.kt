package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.application.domain.Application
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.exception.AlreadyApplyingForPicnicException
import dsm.pick2024.domain.application.port.`in`.ApplicationUseCase
import dsm.pick2024.domain.application.port.out.ExistApplicationByUsernamePort
import dsm.pick2024.domain.application.port.out.SaveApplicationPort
import dsm.pick2024.domain.application.presentation.dto.request.ApplicationRequest
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.ZoneId

@Service
class ApplicationService(
    private val existApplicationByUsernamePort: ExistApplicationByUsernamePort,
    private val saveApplicationPort: SaveApplicationPort,
    private val userFacadeUseCase: UserFacadeUseCase
) : ApplicationUseCase {
    @Transactional
    override fun application(request: ApplicationRequest) {
        val user = userFacadeUseCase.currentUser()

        if (existApplicationByUsernamePort.existsByUserId(user.id!!) == true) {
            throw AlreadyApplyingForPicnicException
        }

        saveApplicationPort.save(
            Application(
                username = user.name,
                reason = request.reason,
                startTime = request.startTime,
                endTime = request.endTime,
                status = Status.QUIET,
                date = LocalDate.now(ZoneId.of("Asia/Seoul")),
                grade = user.grade,
                classNum = user.classNum,
                num = user.num,
                userId = user.xquareId
            )
        )
    }
}
