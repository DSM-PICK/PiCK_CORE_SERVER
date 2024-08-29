package dsm.pick2024.global.annotation

import dsm.pick2024.domain.application.enums.ApplicationType
import dsm.pick2024.global.annotation.common.FormatValidator
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@Constraint(validatedBy = [FormatValidator::class])
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ValidFormat(
    val message: String = "형식이 올바르지 않습니다.",
    val type: ApplicationType,
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
