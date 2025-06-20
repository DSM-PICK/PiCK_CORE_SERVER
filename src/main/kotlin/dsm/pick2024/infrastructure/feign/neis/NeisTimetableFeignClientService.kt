package dsm.pick2024.infrastructure.feign.neis

import com.google.gson.Gson
import dsm.pick2024.domain.timetable.domain.Timetable
import dsm.pick2024.infrastructure.feign.neis.client.NeisFeignClient
import dsm.pick2024.infrastructure.feign.neis.dto.response.NeisFeignClientTimetableResponse
import dsm.pick2024.infrastructure.feign.neis.property.NeisFeignClientRequestProperty
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
    fun getNeisInfoToEntity(baseDay: Long): MutableList<Timetable>? {
        val runDay = LocalDate.now().plusDays(baseDay)
        val neisTimetableServiceInfoString =
            neisFeignClient.hisTimetable(
                key = neisKey,
                type = NeisFeignClientRequestProperty.TYPE,
                pageIndex = NeisFeignClientRequestProperty.PAGE_INDEX,
                pageSize = NeisFeignClientRequestProperty.PAGE_SIZE,
                schoolCode = NeisFeignClientRequestProperty.SD_SCHUL_CODE,
                atptCode = NeisFeignClientRequestProperty.ATPT_OFCDC_CODE,
                //runDay + baseDay 날짜의 전주 일요일 ~ 이번주 일요일의 시간표를 변경한다.
                startedYmd = runDay.with(java.time.DayOfWeek.SUNDAY).minusDays(7).toString().replace("-", ""),
                endedYmd = runDay.with(java.time.DayOfWeek.SUNDAY).toString().replace("-", "")
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
