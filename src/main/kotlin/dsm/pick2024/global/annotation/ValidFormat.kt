package dsm.pick2024.global.annotation

import dsm.pick2024.global.annotation.common.FormatValidator
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@Constraint(validatedBy = [FormatValidator::class])
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ValidFormat(
    val message: String = "형식이 올바르지 않습니다.",
    val groups: Array<KClass<*>> = [], // 어떤 타입의 데이터든 포함할 수 있다
    val payload: Array<KClass<out Payload>> = [] //Payload 타입을 상속받는 어떤 클래스든 수용할 수 있음을 의미
)
