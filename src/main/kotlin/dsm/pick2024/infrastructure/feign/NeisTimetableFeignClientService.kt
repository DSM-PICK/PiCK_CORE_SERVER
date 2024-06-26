package dsm.pick2024.infrastructure.feign

import com.google.gson.Gson
import dsm.pick2024.domain.timetable.domain.Timetable
import dsm.pick2024.infrastructure.feign.client.NeisFeignClient
import dsm.pick2024.infrastructure.feign.client.dto.response.NeisFeignClientTimetableResponse
import dsm.pick2024.infrastructure.feign.client.property.NeisFeignClientRequestProperty
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Component
class NeisTimetableFeignClientService(
    @Value("\${open-feign.neis-key}")
    private val neisKey: String,
    private val neisFeignClient: NeisFeignClient
) {
    fun getNeisInfoToEntity(): MutableList<Timetable>? {
        val runDay = LocalDate.now()

        val neisTimetableServiceInfoString =
            neisFeignClient.hisTimetable(
                key = neisKey,
                type = NeisFeignClientRequestProperty.TYPE,
                pageIndex = NeisFeignClientRequestProperty.PAGE_INDEX,
                pageSize = NeisFeignClientRequestProperty.PAGE_SIZE,
                schoolCode = NeisFeignClientRequestProperty.SD_SCHUL_CODE,
                atptCode = NeisFeignClientRequestProperty.ATPT_OFCDC_CODE,
                startedYmd = runDay.withDayOfMonth(runDay.dayOfMonth).toString().replace("-", ""),
                endedYmd = runDay.withDayOfMonth(runDay.dayOfMonth).plusDays(7).toString().replace("-", "")
            )
        val timetableJson =
            Gson().fromJson(
                neisTimetableServiceInfoString,
                NeisFeignClientTimetableResponse::class.java
            )
        val timetables = mutableListOf<Timetable>()

        for (timetable in timetableJson.hisTimetable) {
            timetable.row?.let { rows ->
                for (row in rows) {
                    val dayWeek = changeStringToLocalDate(row.ALL_TI_YMD).dayOfWeek.value
                    val grade = row.GRADE
                    val classNum = row.CLASS_NM
                    val period = row.PERIO
                    val subjectName = row.ITRT_CNTNT

                    timetables.add(
                        Timetable(
                            grade = grade,
                            classNum = classNum,
                            period = period,
                            subjectName = subjectName,
                            dayWeek = dayWeek
                        )
                    )
                }
            }
        }
        return timetables
    }

    private fun changeStringToLocalDate(date: String): LocalDate {
        return LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE)
    }
}
