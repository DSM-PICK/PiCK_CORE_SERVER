package dsm.pick2024_server.infrastructure.feign.client

import dsm.pick2024_server.infrastructure.feign.client.property.NeisFeignProperty
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "neis-feign-client", url = "https://open.neis.go.kr/hub")
interface NeisFeignClient {

    @GetMapping("/mealServiceDietInfo")
    fun getMealServiceDietInfo(
        @RequestParam(name = NeisFeignProperty.KEY) key: String,
        @RequestParam(name = NeisFeignProperty.TYPE) type: String,
        @RequestParam(name = NeisFeignProperty.PAGE_INDEX) pageIndex: Int,
        @RequestParam(name = NeisFeignProperty.PAGE_SIZE) pageSize: Int,
        @RequestParam(name = NeisFeignProperty.SD_SCHUL_CODE)schoolCode: String,
        @RequestParam(name = NeisFeignProperty.ATPT_OFCDC_SC_CODE)atptCode: String
    ): String

    @GetMapping("/SchoolSchedule")
    fun getSchoolSchedule(
        @RequestParam(name = NeisFeignProperty.KEY) key: String,
        @RequestParam(name = NeisFeignProperty.TYPE) type: String,
        @RequestParam(name = NeisFeignProperty.PAGE_INDEX) pageIndex: Int,
        @RequestParam(name = NeisFeignProperty.PAGE_SIZE) pageSize: Int,
        @RequestParam(name = NeisFeignProperty.SD_SCHUL_CODE)schoolCode: String,
        @RequestParam(name = NeisFeignProperty.ATPT_OFCDC_SC_CODE)atptCode: String
    ): String

    @GetMapping("/hisTimetable")
    fun hisTimetable(
        @RequestParam(name = NeisFeignProperty.KEY) key: String,
        @RequestParam(name = NeisFeignProperty.TYPE) type: String,
        @RequestParam(name = NeisFeignProperty.PAGE_INDEX) pageIndex: Int,
        @RequestParam(name = NeisFeignProperty.PAGE_SIZE) pageSize: Int,
        @RequestParam(name = NeisFeignProperty.SD_SCHUL_CODE)schoolCode: String,
        @RequestParam(name = NeisFeignProperty.ATPT_OFCDC_SC_CODE)atptCode: String,
        @RequestParam(name = NeisFeignProperty.SEM)sem: String,
        @RequestParam(name = NeisFeignProperty.DDDEP_NM)major: String,
        @RequestParam(name = NeisFeignProperty.GRADE)grade: String
    )
}
