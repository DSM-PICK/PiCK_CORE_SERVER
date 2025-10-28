package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.application.enums.ApplicationKind
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.port.`in`.ApplicationFinderUseCase
import dsm.pick2024.domain.application.port.`in`.QueryFloorAndStatusApplicationUseCase
import dsm.pick2024.domain.application.port.out.QueryAllApplicationPort
import dsm.pick2024.domain.application.presentation.dto.response.QueryApplicationResponse
import dsm.pick2024.domain.classroom.exception.FloorNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryFloorApplicationService(
    private val applicationFinderUseCase: ApplicationFinderUseCase,
    private val queryAllApplicationPort: QueryAllApplicationPort
) : QueryFloorAndStatusApplicationUseCase {

    @Transactional(readOnly = true)
    override fun queryFloorAndStatusApplication(floor: Int, status: Status): List<QueryApplicationResponse> {
        val applications = when (floor) {
            2, 3, 4 -> {
                val filterClassroomList =
                    applicationFinderUseCase.findByFloorAndApplicationKindOrThrow(floor, ApplicationKind.APPLICATION)
                filterClassroomList.filter { it.status == status }
            }
            5 -> {
                queryAllApplicationPort.findAllByStatusAndApplicationKind(status, ApplicationKind.APPLICATION)
            }
            else -> throw FloorNotFoundException
        }

        return applications.map { QueryApplicationResponse(it) }.distinctBy { it.id }.sortedWith(
            compareBy({ it.grade }, { it.classNum }, { it.num })
        )
    }
}
