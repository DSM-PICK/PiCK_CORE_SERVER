package dsm.pick2024.domain.status.service

import dsm.pick2024.domain.status.domain.Status
import dsm.pick2024.domain.status.entity.enum.StatusType
import dsm.pick2024.domain.status.port.`in`.SaveAllStatusUserUseCase
import dsm.pick2024.domain.status.port.out.SaveStatusPort
import dsm.pick2024.infrastructure.feign.xquare.client.XquareFeignClient
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SaveAllStatusUserService(
    private val saveStatusPort: SaveStatusPort,
    private val xquareFeignClient: XquareFeignClient
) : SaveAllStatusUserUseCase {
    @Transactional
    override fun saveAll(key: String) {
        val xquareUserInfo = xquareFeignClient.userAll(key)
        val statusEntities =
            xquareUserInfo.map { user ->
                Status(
                    userId = user.id,
                    userName = user.name,
                    grade = user.grade,
                    classNum = user.classNum,
                    num = user.num,
                    status = StatusType.ATTENDANCE
                )
            }.toMutableList()
        saveStatusPort.saveAll(statusEntities)
    }
}
