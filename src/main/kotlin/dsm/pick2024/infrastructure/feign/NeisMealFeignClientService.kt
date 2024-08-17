package dsm.pick2024.infrastructure.feign

import com.google.gson.Gson
import dsm.pick2024.domain.meal.domain.Meal
import dsm.pick2024.domain.meal.enum.MealType
import dsm.pick2024.infrastructure.feign.client.NeisFeignClient
import dsm.pick2024.infrastructure.feign.client.dto.response.NeisFeignClientMealServiceDietInfoResponse
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

    fun getNeisInfoToEntity(): MutableList<Meal> {
        val nextMonth = LocalDate.now().plusMonths(1)

        val neisMealServiceDietInfoString =
            neisFeignClient.getMealServiceDietInfo(
                key = neisKey,
                type = NeisFeignClientRequestProperty.TYPE,
                pageIndex = NeisFeignClientRequestProperty.PAGE_INDEX,
                pageSize = NeisFeignClientRequestProperty.PAGE_SIZE,
                sdSchoolCode = NeisFeignClientRequestProperty.SD_SCHUL_CODE,
                atptCode = NeisFeignClientRequestProperty.ATPT_OFCDC_CODE,
                startedYmd = changeDateTimeFormat(nextMonth.withDayOfMonth(1).toString()),
                endedYmd = changeDateTimeFormat(nextMonth.withDayOfMonth(nextMonth.lengthOfMonth()).toString())
            )

        val mealJson =
            Gson().fromJson(
                neisMealServiceDietInfoString,
                NeisFeignClientMealServiceDietInfoResponse::class.java
            )

        val mealTotalCount = mealJson.mealServiceDietInfo.first().head.first().list_total_count

        val mealEntities = mutableListOf<Meal>()
        val breakfastMap = mutableMapOf<LocalDate, String>()
        val lunchMap = mutableMapOf<LocalDate, String>()
        val dinnerMap = mutableMapOf<LocalDate, String>()

        for (i in 0 until mealTotalCount) {
            val mealRow = getRow(mealJson, i)
            val mealDate = parseDate(mealRow.MLSV_YMD)
            val mealCode = mealRow.MMEAL_SC_CODE
            val menu = formatMenu(mealRow.DDISH_NM)
            val calInfo = mealRow.CAL_INFO

            when (mealCode) {
                "1" -> breakfastMap[mealDate] = menu
                "2" -> lunchMap[mealDate] = menu
                "3" -> dinnerMap[mealDate] = menu
            }

            mealEntities.add(
                Meal(
                    mealDate = mealDate,
                    mealType = getMealType(mealCode),
                    menu = menu,
                    cal = calInfo
                )
            )
        }

        return mealEntities
    }

    private fun getRow(
        response: NeisFeignClientMealServiceDietInfoResponse,
        index: Int
    ) = response.mealServiceDietInfo[1].row[index]

    private fun formatMenu(menu: String): String =
        menu
            .replace("<br/>", "||")
            .replace("/", "&")
            .replace(Regex("[0-9.()]"), "")
            .trim()

    private fun parseDate(date: String): LocalDate {
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        return LocalDate.parse(date, formatter)
    }

    private fun getMenuReplace(
        response: NeisFeignClientMealServiceDietInfoResponse,
        i: Int
    ): Pair<String, String> {
        val menu =
            getRow(response, i).DDISH_NM
                .replace("<br/>", "||")
                .replace("/", "&")
                .replace("[0-9.()]".toRegex(), "")
                .replace("\\p{Z}".toRegex(), "")

        val calInfo = getRow(response, i).CAL_INFO

        return Pair(menu, calInfo)
    }

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

    private fun getMealType(mealCode: String): MealType? = when (mealCode) {
        "1" -> MealType.BREAKFAST
        "2" -> MealType.LUNCH
        "3" -> MealType.DINNER
        else -> null
    }
}
