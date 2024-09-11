package dsm.pick2024.domain.application.port.`in`

import dsm.pick2024.domain.application.enums.ApplicationKind
import java.util.*

interface ApplicationFacadeUseCase {
    fun handleStatusOk(idList: List<UUID>, adminName: String, applicationKind: ApplicationKind)

    fun handleStatusNo(idList: List<UUID>, applicationKind: ApplicationKind)
}
