package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.admin.port.`in`.AdminFinderUseCase
import dsm.pick2024.domain.application.domain.Application
import dsm.pick2024.domain.application.enums.ApplicationKind
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.exception.AlreadyApplyingForPicnicException
import dsm.pick2024.domain.application.port.`in`.ApplicationUseCase
import dsm.pick2024.domain.application.port.out.ExistsApplicationPort
import dsm.pick2024.domain.application.port.out.SaveApplicationPort
import dsm.pick2024.domain.application.presentation.dto.request.ApplicationRequest
import dsm.pick2024.domain.devicetoken.port.out.AdminDeviceTokenPort
import dsm.pick2024.domain.event.dto.UserInfoRequest
import dsm.pick2024.domain.event.enums.EventTopic
import dsm.pick2024.domain.fcm.port.out.FcmSendPort
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.ZoneId

@Service
class ApplicationService(
    private val existsApplicationPort: ExistsApplicationPort,
    private val saveApplicationPort: SaveApplicationPort,
    private val userFacadeUseCase: UserFacadeUseCase,
    private val eventPublisher: ApplicationEventPublisher,
    private val fcmSendPort: FcmSendPort,
    private val adminFinderUseCase: AdminFinderUseCase,
    private val adminDeviceTokenPort: AdminDeviceTokenPort
) : ApplicationUseCase {

    @Transactional
    override fun application(request: ApplicationRequest) {
        val user = userFacadeUseCase.currentUser()

        if (existsApplicationPort.existByUserId(user.id)) {
            throw AlreadyApplyingForPicnicException
        }

        saveApplicationPort.save(
            Application(
                userName = user.name,
                reason = request.reason,
                start = request.start,
                end = request.end,
                status = Status.QUIET,
                date = LocalDate.now(ZoneId.of("Asia/Seoul")),
                grade = user.grade,
                classNum = user.classNum,
                num = user.num,
                userId = user.id,
                applicationType = request.applicationType,
                applicationKind = ApplicationKind.APPLICATION
            )
        )

        val admin = adminFinderUseCase.findByGradeAndClassNumOrThrow(
            grade = user.grade,
            classNum = user.classNum
        )

        val deviceTokens = adminDeviceTokenPort.findAllByAdminId(admin.id)
            .map { it.deviceToken }
            .filter { it.isNotBlank() }

        deviceTokens.forEach { token ->
            fcmSendPort.send(
                deviceToken = token,
                title = "[PiCK] ${user.grade}학년 ${user.classNum}반 ${user.num}번 ${user.name} 학생이 외출을 신청했습니다.",
                body = "사유: ${request.reason}"
            )
        }

        eventPublisher.publishEvent(UserInfoRequest(EventTopic.HANDLE_EVENT, user.id))
    }
}
