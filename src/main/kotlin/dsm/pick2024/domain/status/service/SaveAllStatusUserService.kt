package dsm.pick2024.domain.status.service

import dsm.pick2024.domain.status.domain.Status
import dsm.pick2024.domain.status.entity.enum.StatusType
import dsm.pick2024.domain.status.port.`in`.SaveAllStatusUserUseCase
import dsm.pick2024.domain.status.port.out.SaveAllStatusPort
import dsm.pick2024.infrastructure.feign.client.XquareFeignClient
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SaveAllStatusUserService(
    private val saveAllStatusPort: SaveAllStatusPort,
    private val xquareFeignClient: XquareFeignClient
) : SaveAllStatusUserUseCase {
    @Transactional
    override fun saveAll(key: String) {
        val xquareUserInfo = xquareFeignClient.userAll(key)
        val statusEntities =
            xquareUserInfo.map { user ->
                Status(
                    userId = user.id,
                    username = user.name,
                    grade = user.grade,
                    classNum = user.classNum,
                    num = user.num,
                    type = StatusType.ATTENDANCE
                )
            }.toMutableList()
        saveAllStatusPort.saveAll(statusEntities)
    }
}
