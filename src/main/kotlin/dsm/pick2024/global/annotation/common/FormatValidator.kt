package dsm.pick2024.global.annotation.common

import dsm.pick2024.domain.application.enums.ApplicationType
import dsm.pick2024.domain.application.presentation.dto.request.ApplicationRequest
import dsm.pick2024.global.annotation.ValidFormat
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import java.time.LocalTime

class FormatValidator : ConstraintValidator<ValidFormat, ApplicationRequest> {

    override fun isValid(request: ApplicationRequest, context: ConstraintValidatorContext): Boolean {
        return when (request.applicationType) {
            ApplicationType.TIME -> isValidTimeFormat(request.start) && isValidTimeFormat(request.end)
            ApplicationType.PERIOD -> isValidPeriodFormat(request.start) && isValidPeriodFormat(request.end)
        }
    }

    private fun isValidTimeFormat(time: String): Boolean {
        return try {
            LocalTime.parse(time)
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun isValidPeriodFormat(period: String): Boolean {
        return Regex("""\d+교시""").matches(period)
    }
}
