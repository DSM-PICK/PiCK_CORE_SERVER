package dsm.pick2024.global.common

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

object TimeUtils {
    val KST_ZONE_ID: ZoneId = ZoneId.of("Asia/Seoul")

    fun nowLocalDate(): LocalDate {
        return LocalDate.now(KST_ZONE_ID)
    }
    fun nowLocalDateTime(): LocalDateTime {
        return LocalDateTime.now(KST_ZONE_ID)
    }
}
