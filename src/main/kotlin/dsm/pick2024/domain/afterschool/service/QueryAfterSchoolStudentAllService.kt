package dsm.pick2024.domain.afterschool.service

import dsm.pick2024.domain.afterschool.port.`in`.QueryAfterSchoolStudentAllUseCase
import dsm.pick2024.domain.afterschool.port.out.QueryAfterSchoolStudentAllPort
import dsm.pick2024.domain.afterschool.presentation.dto.response.QueryAfterSchoolStudentAllResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryAfterSchoolStudentAllService(
    private val queryAfterSchoolStudentAllPort: QueryAfterSchoolStudentAllPort
): QueryAfterSchoolStudentAllUseCase {

    @Transactional(readOnly = true)
    override fun queryAfterSchoolStudentAll() =
        queryAfterSchoolStudentAllPort.findByAll()
            .map {
                it ->
                QueryAfterSchoolStudentAllResponse(
                    it.grade,
                    it.classNum,
                    it.num,
                    it.name,
                    it.status
                )
            }

}
