package dsm.pick2024.domain.timetable.service

import dsm.pick2024.domain.timetable.domain.Timetable
import dsm.pick2024.domain.timetable.port.`in`.SaveTimetableUseCase
import dsm.pick2024.domain.timetable.port.out.SaveTimetablePort
import dsm.pick2024.infrastructure.feign.NeisTimetableFeignClientService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SaveTimetableService(
    private val saveAllTimetablePort: SaveTimetablePort,
    private val neisTimetableFeignClientService: NeisTimetableFeignClientService
) : SaveTimetableUseCase {

    @Transactional
    override fun saveTimetable() {
        val timetableEntities = neisTimetableFeignClientService.getNeisInfoToEntity()

        val updatedTimetableEntities = timetableEntities?.mapNotNull { timetable ->
            val subject = updatedSubjectName(timetable.subjectName)

            if (subject == "토요휴업일") {
                null
            } else {
                Timetable(
                    id = timetable.id,
                    grade = timetable.grade,
                    classNum = timetable.classNum,
                    period = timetable.period,
                    subjectName = subject,
                    dayWeek = timetable.dayWeek
                )
            }
        }?.toMutableList()

        updatedTimetableEntities?.let { saveAllTimetablePort.saveAll(it) }
    }

    private fun updatedSubjectName(subjectName: String): String {
        val cleanName = subjectName.replace(Regex("[\\[\\(].*?[\\]\\)]|\\d|공통"), "")

        return when (cleanName) {
            "* 데이터베이스 구현", "* SQL활용" -> "DB 프로그래밍"
            "* 응용SW 변경관리" -> "응용SW 변경관리"
            "* 응용SW 운영관리" -> "디지털 포렌식"
            "* 응용SW 기초 기술 활용" -> "응용SW엔지니어링"
            "* 정보통신기기 PCB보드 개발" -> "정보통신기기 PCB보드 개발"
            "* 정보통신기기 펌웨어구현" -> "정보통신기기 펌웨어구현"
            "* 빅데이터 분석 결과 시각화", "빅데이터 분석 결과 시각화", "* 분석 데이터 전처리" -> "빅데이터 분석"
            "* 시스템 유지보수관리" -> "시스템 유지보수관리"
            "* 시스템 점검관리" -> "시스템 점검관리"
            "자율활동", "동아리활동", "자율·자치활동" -> "창체"
            "자료구조와 알고리즘" -> "자료구조"
            "미술 창작" -> "미술"
            "* 화면 구현" -> "화면디자인"
            "서버 구축 및 운영" -> "서버 프로그래밍 실무"
            else -> cleanName
        }
    }
}
