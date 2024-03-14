package dsm.pick2024.infrastructure.feign

import com.google.gson.Gson
import dsm.pick2024.domain.timetable.entity.TimetableJpaEntity
import dsm.pick2024.infrastructure.feign.client.NeisFeignClient
import dsm.pick2024.infrastructure.feign.client.property.NeisFeignClientRequestProperty
import dsm.pick2024.infrastructure.feign.client.response.NeisFeignClientTimetableResponse
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class NeisTimetableFeignClientService(
    @Value("\${open-feign.neis-key}")
    private val neisKey: String,
    private val neisFeignClient: NeisFeignClient
) {
    fun getNeisInfoToEntity(): MutableList<TimetableJpaEntity>? {
        val runDay = LocalDate.now()

        val neisTimetableServiceInfoString = neisFeignClient.hisTimetable(
            key = neisKey,
            type = NeisFeignClientRequestProperty.TYPE,
            pageIndex = NeisFeignClientRequestProperty.PAGE_INDEX,
            pageSize = NeisFeignClientRequestProperty.PAGE_SIZE,
            schoolCode = NeisFeignClientRequestProperty.SD_SCHUL_CODE,
            atptCode = NeisFeignClientRequestProperty.ATPT_OFCDC_CODE,
            startedYmd = runDay.withDayOfMonth(runDay.dayOfMonth).toString().replace("-", ""),
            endedYmd = runDay.withDayOfMonth(runDay.dayOfMonth).plusDays(7).toString().replace("-", "")
        )
        val timetableJson = Gson().fromJson(
            neisTimetableServiceInfoString,
            NeisFeignClientTimetableResponse::class.java
        )
        val timetableEntities = mutableListOf<TimetableJpaEntity>()

        for (timetable in timetableJson.hisTimetable) {
            timetable.row?.let { rows ->
                for (row in rows) {
                    val dayWeek = changeStringToLocalDate(row.ALL_TI_YMD).dayOfWeek.value
                    val grade = row.GRADE
                    val classNum = row.CLASS_NM
                    val period = row.PERIO
                    val subjectName = row.ITRT_CNTNT

                    timetableEntities.add(
                        TimetableJpaEntity(
                            id = null,
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
        return timetableEntities
    }
    private fun changeStringToLocalDate(date: String): LocalDate {
        return LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE)
    }
}
