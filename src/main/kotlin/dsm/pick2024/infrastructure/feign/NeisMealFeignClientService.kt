package dsm.pick2024.infrastructure.feign

import com.google.gson.Gson
import dsm.pick2024.domain.meal.entity.MealJpaEntity
import dsm.pick2024.infrastructure.feign.client.NeisFeignClient
import dsm.pick2024.infrastructure.feign.client.response.NeisFeignClientMealServiceDietInfoResponse
import dsm.pick2024.infrastructure.feign.client.property.NeisFeignClientRequestProperty
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Component
class NeisMealFeignClientService(
    @Value("\${open-feign.neis-key}")
    private val neisKey: String,
    private val neisFeignClient: NeisFeignClient
) {

    fun getNeisInfoToEntity(): MutableList<MealJpaEntity> {
        val nextMonth = LocalDate.now()

        val neisMealServiceDietInfoString = neisFeignClient.getMealServiceDietInfo(
            key = neisKey,
            type = NeisFeignClientRequestProperty.TYPE,
            pageIndex = NeisFeignClientRequestProperty.PAGE_INDEX,
            pageSize = NeisFeignClientRequestProperty.PAGE_SIZE,
            sdSchoolCode = NeisFeignClientRequestProperty.SD_SCHUL_CODE,
            atptCode = NeisFeignClientRequestProperty.ATPT_OFCDC_CODE,
            startedYmd = changeDateTimeFormat(nextMonth.withDayOfMonth(1).toString()),
            endedYmd = changeDateTimeFormat(nextMonth.withDayOfMonth(nextMonth.lengthOfMonth()).toString())
        )

        val mealJson = Gson().fromJson(
            neisMealServiceDietInfoString,
            NeisFeignClientMealServiceDietInfoResponse::class.java
        )
        val mealTotalCount = mealJson.mealServiceDietInfo.first().head.first().list_total_count

        val mealEntities = mutableListOf<MealJpaEntity>()
        val mealCodes = mutableListOf<String>()

        val breakfastMap = mutableMapOf<LocalDate, Pair<String, String>>()
        val lunchMap = mutableMapOf<LocalDate, Pair<String, String>>()
        val dinnerMap = mutableMapOf<LocalDate, Pair<String, String>>()

        for (i: Int in 0 until mealTotalCount) {
            val mealCode = getMealCode(mealJson, i)
            val calInfo = getCalInfo(mealJson, i)
            val menu = getMenuReplace(mealJson, i)
            val mealDate = getMealDate(mealJson, i)

            val transferMealDate = changeDateTimeFormat(mealDate)
            val mealLocalDate = stringToLocalDate(transferMealDate)

            mealCodes.add(
                index = i,
                element = mealCode
            )

            when (mealCode) {
                "1" -> breakfastMap[mealLocalDate] = Pair(menu.first, calInfo)
                "2" -> lunchMap[mealLocalDate] = Pair(menu.first, calInfo)
                "3" -> dinnerMap[mealLocalDate] = Pair(menu.first, calInfo)
            }

            mealEntities.add(
                MealJpaEntity(
                    id = null,
                    mealDate = mealLocalDate,
                    breakfast = breakfastMap[mealLocalDate]?.first.orEmpty(),
                    lunch = lunchMap[mealLocalDate]?.first.orEmpty(),
                    dinner = dinnerMap[mealLocalDate]?.first.orEmpty()
                )
            )
        }
        return mealEntities
    }

    private fun getRow(response: NeisFeignClientMealServiceDietInfoResponse, i: Int) =
        response.mealServiceDietInfo[1].row[i]

    private fun getMealCode(response: NeisFeignClientMealServiceDietInfoResponse, i: Int) =
        getRow(response, i).MMEAL_SC_CODE

    private fun getCalInfo(response: NeisFeignClientMealServiceDietInfoResponse, i: Int) = getRow(response, i).CAL_INFO

    private fun getMenuReplace(response: NeisFeignClientMealServiceDietInfoResponse, i: Int): Pair<String, String> {
        val menu = getRow(response, i).DDISH_NM
            .replace("<br/>", "||")
            .replace("/", "&")
            .replace("[0-9.()]".toRegex(), "")
            .replace("\\p{Z}".toRegex(), "")

        val calInfo = getRow(response, i).CAL_INFO

        return Pair(menu, calInfo)
    }

    private fun getMealDate(response: NeisFeignClientMealServiceDietInfoResponse, i: Int) = getRow(response, i).MLSV_YMD

    private fun changeDateTimeFormat(date: String): String =
        if (date.length > 8) {
            date.replace("-", "")
        } else {
            val sb = StringBuffer()
            sb.append(date)
            sb.insert(4, "-")
            sb.insert(7, "-")
            sb.toString()
        }

    private fun stringToLocalDate(transferMealDate: String): LocalDate {
        val passer = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return LocalDate.parse(transferMealDate, passer)
    }
}
