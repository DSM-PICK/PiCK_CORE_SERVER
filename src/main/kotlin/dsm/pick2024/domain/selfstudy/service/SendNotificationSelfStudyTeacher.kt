package dsm.pick2024.domain.selfstudy.service

import dsm.pick2024.domain.admin.port.`in`.AdminFinderUseCase
import dsm.pick2024.domain.devicetoken.port.out.QueryAdminDeviceTokenPort
import dsm.pick2024.domain.outbox.port.`in`.OutboxFacadeUseCase
import dsm.pick2024.domain.selfstudy.port.`in`.SelfStudyFinderUseCase
import dsm.pick2024.domain.selfstudy.port.`in`.SendNotificationSelfStudyTeacherUseCase
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class SendNotificationSelfStudyTeacher(
    private val adminFinderUseCase: AdminFinderUseCase,
    private val selfStudyFinderUseCase: SelfStudyFinderUseCase,
    private val outboxFacadeUseCase: OutboxFacadeUseCase,
    private val queryAdminDeviceTokenPort: QueryAdminDeviceTokenPort
) : SendNotificationSelfStudyTeacherUseCase {
    override fun execute() {
        val selfStudies = selfStudyFinderUseCase.findByDaySelfStudyOrThrow(LocalDate.now()).map { it }

        selfStudies.forEach { selfStudy ->
            val admin = adminFinderUseCase.findByAdminIdOrThrow(selfStudy.teacherName)
            val tokens = queryAdminDeviceTokenPort.findAllByAdminId(admin.id)
                .map { it.deviceToken }

            tokens.forEach { token ->
                outboxFacadeUseCase.sendNotification(
                    deviceToken = token,
                    title = "[PiCK] 자습감독 알림",
                    body = "${admin.name} 선생님은 오늘 ${selfStudy.floor}층 자습감독 선생님입니다."
                )
            }
        }
    }
}
