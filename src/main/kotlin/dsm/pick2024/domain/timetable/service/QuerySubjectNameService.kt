package dsm.pick2024.domain.timetable.service

import dsm.pick2024.domain.timetable.enum.Subject
import dsm.pick2024.domain.timetable.port.`in`.QuerySubjectNameUseCase
import org.springframework.stereotype.Service

@Service
class QuerySubjectNameService() : QuerySubjectNameUseCase {

    override fun getAllSubjectNames(): List<String> {
        return Subject.values().map { it.subjectName }
    }
}
