package dsm.pick2024.infrastructure.feign.neis.dto.request

data class NeisFeignClientMealServiceDietInfoResponse(
    val mealServiceDietInfo: List<MealServiceDietInfo>
)

data class MealServiceDietInfo(
    val head: List<NeisMealHead>,
    val row: List<NeisMealRow>
)

data class NeisMealHead(
    val list_total_count: Int
)

data class NeisMealRow(
    val MMEAL_SC_CODE: String,
    val MLSV_YMD: String,
    val DDISH_NM: String,
    val CAL_INFO: String
)
