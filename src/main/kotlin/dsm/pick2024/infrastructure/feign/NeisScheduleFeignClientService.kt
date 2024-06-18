package dsm.pick2024.infrastructure.feign

import com.google.gson.Gson
import dsm.pick2024.domain.schedule.domain.Schedule
import dsm.pick2024.infrastructure.feign.client.NeisFeignClient
import dsm.pick2024.infrastructure.feign.client.dto.response.NeisFeignClientScheduleResponse
import dsm.pick2024.infrastructure.feign.client.property.NeisFeignClientRequestProperty
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class NeisScheduleFeignClientService(
    @Value("\${open-feign.neis-key}")
    private val neisKey: String,
    private val neisFeignClient: NeisFeignClient
) {
    fun getNeisInfoToEntity(
        start: String,
        end: String
    ): MutableList<Schedule>? {
        val neisScheduleServiceInfoString =
            neisFeignClient.schoolSchedule(
                key = neisKey,
                type = NeisFeignClientRequestProperty.TYPE,
                pageIndex = NeisFeignClientRequestProperty.PAGE_INDEX,
                pageSize = NeisFeignClientRequestProperty.PAGE_SIZE,
                schoolCode = NeisFeignClientRequestProperty.SD_SCHUL_CODE,
                atptCode = NeisFeignClientRequestProperty.ATPT_OFCDC_CODE,
                startedYmd = start,
                endedYmd = end
            )

        val scheduleJson =
            Gson().fromJson(
                neisScheduleServiceInfoString,
                NeisFeignClientScheduleResponse::class.java
            )

        val schedules = mutableListOf<Schedule>()

        scheduleJson.SchoolSchedule.forEach { schedule ->
            schedule.row?.let { rows ->
                rows.forEach { row ->
                    val date = changeStringToLocalDate(row.AA_YMD)
                    val eventName = row.EVENT_NM
                    schedules.add(
                        Schedule(
                            eventName = eventName,
                            date = date
                        )
                    )
                }
            }
        }
        return schedules
    }

    private fun changeStringToLocalDate(date: String): LocalDate {
        return LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE)
    }
}
