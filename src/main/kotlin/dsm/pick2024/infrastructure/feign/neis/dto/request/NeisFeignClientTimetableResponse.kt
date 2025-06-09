package dsm.pick2024.infrastructure.feign.neis.dto.request

data class NeisFeignClientTimetableResponse(
    val hisTimetable: List<HisTimetable>
)

data class HisTimetable(
    val head: List<NeisTimeTableHead>,
    val row: List<NeisTimetableRow>
)

data class NeisTimeTableHead(
    val list_total_count: Int
)

data class NeisTimetableRow(
    val ALL_TI_YMD: String,
    val GRADE: Int,
    val CLASS_NM: Int,
    val PERIO: Int,
    val ITRT_CNTNT: String
)
