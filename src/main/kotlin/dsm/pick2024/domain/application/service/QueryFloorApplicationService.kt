package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.application.enums.ApplicationKind
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.port.`in`.QueryFloorAndStatusApplicationUseCase
import dsm.pick2024.domain.application.port.out.QueryAllApplicationPort
import dsm.pick2024.domain.application.port.out.QueryApplicationPort
import dsm.pick2024.domain.application.presentation.dto.response.QueryApplicationResponse
import dsm.pick2024.domain.classroom.exception.FloorNotFoundException
import org.joda.time.LocalDate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryFloorApplicationService(
    private val queryApplicationPort: QueryApplicationPort,
    private val queryAllApplicationPort: QueryAllApplicationPort
) : QueryFloorAndStatusApplicationUseCase {

    @Transactional(readOnly = true)
    override fun queryFloorAndStatusApplication(floor: Int, status: Status): List<QueryApplicationResponse> {
        val today = LocalDate.now().dayOfWeek

        val applications = when (floor) {
            2, 3, 4 -> {
                val filteredClassrooms = if (today == 2 || today == 5) {
                    queryApplicationPort.queryApplicationWithAttendance(floor)
                } else {
                    queryApplicationPort.findByFloorAndApplicationKind(floor, ApplicationKind.APPLICATION)
                }
                filteredClassrooms.filter { it.status == status }
            }
            5 -> {
                queryAllApplicationPort.findAllByStatusAndApplicationKind(status, ApplicationKind.APPLICATION)
            }
            else -> throw FloorNotFoundException
        }

        return applications.map { QueryApplicationResponse(it) }.sortedWith(
            compareBy({ it.grade }, { it.classNum }, { it.num })
        )
    }
}
