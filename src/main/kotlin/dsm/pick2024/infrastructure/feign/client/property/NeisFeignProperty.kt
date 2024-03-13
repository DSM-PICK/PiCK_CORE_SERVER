package dsm.pick2024.infrastructure.feign.client.property

object NeisFeignProperty {
    const val KEY = "KEY"
    const val TYPE = "Type"
    const val PAGE_INDEX = "pIndex"
    const val PAGE_SIZE = "pSize"
    const val SD_SCHUL_CODE = "SD_SCHUL_CODE"
    const val ATPT_OFCDC_SC_CODE = "ATPT_OFCDC_SC_CODE"
    const val STARTED_YMD = "MLSV_FROM_YMD"
    const val ENDED_YMD = "MLSV_TO_YMD"
    const val TI_FROM_YMD = "TI_FROM_YMD"
    const val TI_TO_YMD = "TI_TO_YMD"
}

object NeisFeignClientRequestProperty {
    const val TYPE = "json"
    const val PAGE_INDEX = 1
    const val PAGE_SIZE = 1000
    const val SD_SCHUL_CODE = "7430310"
    const val ATPT_OFCDC_CODE = "G10"
}
