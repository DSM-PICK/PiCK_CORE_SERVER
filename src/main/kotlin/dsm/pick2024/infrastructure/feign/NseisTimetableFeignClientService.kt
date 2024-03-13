package dsm.pick2024.infrastructure.feign

import com.google.gson.Gson
import dsm.pick2024.infrastructure.feign.client.property.NeisFeignClientRequestProperty
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import dsm.pick2024.domain.timetable.entity.TimetableJpaEntity
import dsm.pick2024.infrastructure.feign.client.NeisFeignClient
import dsm.pick2024.infrastructure.feign.client.response.NeisFeignClientMealServiceDietInfoResponse
import dsm.pick2024.infrastructure.feign.client.response.NeisFeignClientTimetableResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class NeisTimetableFeignClientService(
    @Value("\${open-feign.neis-key}")
    private val neisKey: String,
    private val neisFeignClient: NeisFeignClient,
) {

    fun getNeisInfoToEntity(): MutableList<TimetableJpaEntity>? {
        val nextMonth = LocalDate.now()

        val neisTimetableServiceInfoString = neisFeignClient.hisTimetable(
            key = neisKey,
            type = NeisFeignClientRequestProperty.TYPE,
            pageIndex = NeisFeignClientRequestProperty.PAGE_INDEX,
            pageSize = NeisFeignClientRequestProperty.PAGE_SIZE,
            schoolCode = NeisFeignClientRequestProperty.SD_SCHUL_CODE,
            atptCode = NeisFeignClientRequestProperty.ATPT_OFCDC_CODE,
            startedYmd = nextMonth.withDayOfMonth(1).toString().replace("-", ""),
            endedYmd = nextMonth.withDayOfMonth(nextMonth.lengthOfMonth()).toString().replace("-", "")
        )

        val timetableJson = Gson().fromJson(
            neisTimetableServiceInfoString,
            NeisFeignClientTimetableResponse::class.java
        )
        val timetableEntities = mutableListOf<TimetableJpaEntity>()

        for (timetable in timetableJson.hisTimetable) {
            timetable.row?.let { rows ->
                for (row in rows) {
                    val date = changeStringToLocalDate(row.ALL_TI_YMD)
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
                            date = date
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
