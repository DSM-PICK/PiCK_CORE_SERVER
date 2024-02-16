package dsm.pick2024.domain.afterschool.port.`in`

import dsm.pick2024.domain.afterschool.presentation.dto.request.SaveAfterSchoolRequest

interface SaveAfterSchoolUseCase {
    fun saveAfterSchoolUseCase(request: SaveAfterSchoolRequest)
}
