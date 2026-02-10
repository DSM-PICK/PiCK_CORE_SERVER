package dsm.pick2024.domain.selfstudy.service

import dsm.pick2024.domain.admin.port.`in`.AdminFinderUseCase
import dsm.pick2024.domain.outbox.port.`in`.OutboxFacadeUseCase
import dsm.pick2024.domain.selfstudy.port.`in`.SelfStudyFinderUseCase
import dsm.pick2024.domain.selfstudy.port.`in`.SendNotificationSelfStudyTeacherUseCase
import dsm.pick2024.global.common.TimeUtils
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class SendNotificationSelfStudyTeacher(
    private val adminFinderUseCase: AdminFinderUseCase,
    private val selfStudyFinderUseCase: SelfStudyFinderUseCase,
    private val outboxFacadeUseCase: OutboxFacadeUseCase
) : SendNotificationSelfStudyTeacherUseCase {
    override fun execute() {
        val selfStudies = selfStudyFinderUseCase.findByDaySelfStudyOrThrow(TimeUtils.nowLocalDate())

        selfStudies.forEach { selfStudy ->
            val admin = adminFinderUseCase.findByAdminNameOrThrow(selfStudy.teacherName)
            admin.deviceToken?.let { token ->
                outboxFacadeUseCase.sendNotification(
                    deviceToken = token,
                    title = "[PiCK] 자습감독 알림",
                    body = "${admin.name}선생님은 오늘 ${selfStudy.floor}층 자습감독 선생님입니다."
                )
            }
        }
    }
}
