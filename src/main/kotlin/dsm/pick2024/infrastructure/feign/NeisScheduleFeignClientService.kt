package dsm.pick2024.infrastructure.feign

import com.google.gson.Gson
import dsm.pick2024.domain.schedule.entity.ScheduleJpaEntity
import dsm.pick2024.infrastructure.feign.client.NeisFeignClient
import dsm.pick2024.infrastructure.feign.client.property.NeisFeignClientRequestProperty
import dsm.pick2024.infrastructure.feign.client.response.NeisFeignClientScheduleResponse
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class NeisScheduleFeignClientService(
    @Value("\${open-feign.neis-key}")
    private val neisKey: String,
    private val neisFeignClient: NeisFeignClient
) {
    fun getNeisInfoToEntity(start: String, end: String): MutableList<ScheduleJpaEntity>? {

        val neisScheduleServiceInfoString = neisFeignClient.schoolSchedule(
            key = neisKey,
            type = NeisFeignClientRequestProperty.TYPE,
            pageIndex = NeisFeignClientRequestProperty.PAGE_INDEX,
            pageSize = NeisFeignClientRequestProperty.PAGE_SIZE,
            schoolCode = NeisFeignClientRequestProperty.SD_SCHUL_CODE,
            atptCode = NeisFeignClientRequestProperty.ATPT_OFCDC_CODE,
            startedYmd = start,
            endedYmd = end
        )

        val scheduleJson = Gson().fromJson(
            neisScheduleServiceInfoString,
            NeisFeignClientScheduleResponse::class.java
        )

        val scheduleEntities = mutableListOf<ScheduleJpaEntity>()

        scheduleJson.SchoolSchedule.forEach { schedule ->
            schedule.row?.let { rows ->
                rows.forEach { row ->
                    val date = changeStringToLocalDate(row.AA_YMD)
                    val eventName = row.EVENT_NM
                    scheduleEntities.add(
                        ScheduleJpaEntity(
                            id = null,
                            eventName = eventName,
                            date = date
                        )
                    )
                }
            }
        }
        return scheduleEntities
    }

    private fun changeStringToLocalDate(date: String): LocalDate {
        return LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE)
    }
}
