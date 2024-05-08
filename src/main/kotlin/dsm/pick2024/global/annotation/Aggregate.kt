package dsm.pick2024.global.annotation

// domain의 일관성을 기준이 되는 Aggregate를 명시하는 어노테이션

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class Aggregate()
