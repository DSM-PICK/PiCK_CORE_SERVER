package dsm.pick2024.infrastructure.feign.neis.dto.response

data class NeisFeignClientScheduleResponse(
    val SchoolSchedule: List<SchoolSchedule>
)

data class SchoolSchedule(
    val row: List<Row>? = null
)

data class Row(
    val AA_YMD: String,
    val EVENT_NM: String
)
