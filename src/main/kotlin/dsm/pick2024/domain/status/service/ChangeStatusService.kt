package dsm.pick2024.domain.status.service

import dsm.pick2024.domain.status.domain.Status
import dsm.pick2024.domain.status.exception.StatusNotFoundException
import dsm.pick2024.domain.status.port.`in`.ChangeStatusUseCase
import dsm.pick2024.domain.status.port.out.FindStatusByUserId
import dsm.pick2024.domain.status.port.out.SaveAllStatusPort
import dsm.pick2024.domain.status.presentation.dto.request.ChangeStatusRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChangeStatusService(
    private val findStatusByUserId: FindStatusByUserId,
    private val saveAllStatusPort: SaveAllStatusPort
) : ChangeStatusUseCase {
    @Transactional
    override fun changeStatus(request: ChangeStatusRequest) {
        val statusUpdate = mutableListOf<Status>()
        request.list.map {
                requests ->
            val period = requests.period
            requests.statusList.map {
                    it ->
                val status = findStatusByUserId.findStatusByUserId(it.userId)
                    ?: throw StatusNotFoundException

                val add = when (period){
                    6 -> status.copy(period6 = it.statusType)
                    7 -> status.copy(period7 = it.statusType)
                    8 -> status.copy(period8 = it.statusType)
                    9 -> status.copy(period9 = it.statusType)
                    10 -> status.copy(period10 = it.statusType)
                    else -> throw Exception("adsf")
                }
                statusUpdate.add(add)
            }
        }
        saveAllStatusPort.saveAll(statusUpdate)
        }

}
