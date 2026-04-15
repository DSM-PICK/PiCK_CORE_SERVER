package dsm.pick2024.domain.status.service

import dsm.pick2024.domain.status.domain.Status
import dsm.pick2024.domain.status.entity.enum.StatusType
import dsm.pick2024.domain.status.port.`in`.SaveAllStatusUserUseCase
import dsm.pick2024.domain.status.port.out.QueryStatusPort
import dsm.pick2024.domain.status.port.out.SaveStatusPort
import dsm.pick2024.domain.user.port.out.QueryUserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SaveAllStatusUserService(
    private val saveStatusPort: SaveStatusPort,
    private val queryStatusPort: QueryStatusPort,
    private val queryUserPort: QueryUserPort
) : SaveAllStatusUserUseCase {
    @Transactional
    override fun saveAll(key: String) {
        val allUsers = queryUserPort.findAll()

        // 기존 상태 보존 (중복 행이 있을 경우 외출/조기귀가 상태 우선)
        val currentStatusByUserId = queryStatusPort.findAll()
            .groupBy { it.userId }
            .mapValues { (_, statuses) ->
                statuses.firstOrNull { it.status != StatusType.ATTENDANCE }?.status
                    ?: StatusType.ATTENDANCE
            }

        // 기존 행 전체 삭제 (중복 정리 + 탈퇴 학생 제거)
        val allExistingUserIds = currentStatusByUserId.keys.toList()
        saveStatusPort.deleteByUserIds(allExistingUserIds)

        // pick DB 전체 학생 기준으로 재삽입 (상태 복원)
        val toSave = allUsers.map { user ->
            Status(
                userId = user.id,
                userName = user.name,
                grade = user.grade,
                classNum = user.classNum,
                num = user.num,
                status = currentStatusByUserId[user.id] ?: StatusType.ATTENDANCE
            )
        }.toMutableList()

        saveStatusPort.saveAll(toSave)
    }
}
