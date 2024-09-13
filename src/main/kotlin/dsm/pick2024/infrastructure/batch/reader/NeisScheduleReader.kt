package dsm.pick2024.infrastructure.batch.reader

import dsm.pick2024.infrastructure.feign.NeisScheduleFeignClientService
import org.springframework.stereotype.Component

@Component
class NeisScheduleReader(
    private val neisScheduleFeignClientService: NeisScheduleFeignClientService
) {

}
