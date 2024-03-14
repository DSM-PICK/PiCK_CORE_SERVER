package dsm.pick2024.infrastructure.feign.client.response

data class NeisFeignClientScheduleResponse(
    val schoolSchedule: List<SchoolSchedule>
)

data class SchoolSchedule(
    val head: List<NeisScheduleHead>,
    val row: List<NeisScheduleRow>
)

data class NeisScheduleHead(
    val list_total_count: Int
)

data class NeisScheduleRow(
    val EVENT_NM: String,
    val AA_YMD: String
)
