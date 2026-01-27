package dsm.pick2024.global.annotation.common

import dsm.pick2024.domain.application.enums.ApplicationType
import dsm.pick2024.domain.application.presentation.dto.request.ApplicationRequest
import dsm.pick2024.domain.earlyreturn.presentation.dto.request.CreateEarlyReturnRequest
import dsm.pick2024.global.annotation.ValidFormat
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import java.time.LocalTime
import java.time.format.DateTimeParseException

class FormatValidator : ConstraintValidator<ValidFormat, Any> {

    override fun isValid(request: Any, context: ConstraintValidatorContext): Boolean {
        return when (request) {
            is ApplicationRequest -> validateApplicationRequest(request)
            is CreateEarlyReturnRequest -> validateCreateEarlyReturnRequest(request)
            else -> false
        }
    }

    private fun validateApplicationRequest(request: ApplicationRequest): Boolean {
        return if (request.applicationType == ApplicationType.PERIOD) {
            isValidPeriodFormat(request.start) && isValidPeriodFormat(request.end)
        } else {
            isValidTimeFormat(request.start) && isValidTimeFormat(request.end)
        }
    }

    private fun validateCreateEarlyReturnRequest(request: CreateEarlyReturnRequest): Boolean {
        return if (request.applicationType == ApplicationType.PERIOD) {
            isValidPeriodFormat(request.start)
        } else {
            isValidTimeFormat(request.start)
        }
    }

    private fun isValidPeriodFormat(period: String): Boolean {
        return Regex("""\d+교시""").matches(period)
    }

    private fun isValidTimeFormat(time: String): Boolean {
        return try {
            LocalTime.parse(time)
            true
        } catch (e: DateTimeParseException) {
            false
        }
    }
}
