package dsm.pick2024.global.config.async

import org.slf4j.LoggerFactory
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
import org.springframework.context.annotation.Configuration
import org.springframework.retry.annotation.EnableRetry
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor
import java.util.concurrent.RejectedExecutionException

/**
 * 비동기 실행 및 재시도 로직 설정
 *
 * @EnableAsync: Debezium 커넥터 초기화 등 비동기 작업 활성화
 * - ThreadPoolTaskExecutor를 사용하여 스레드 풀 관리
 * - corePoolSize: 2 (기본 스레드 수)
 * - maxPoolSize: 5 (최대 스레드 수)
 * - queueCapacity: 10 (대기 큐 크기)
 *
 * @EnableRetry: Debezium 커넥터 등록 시 재시도 로직 활성화
 * - 최대 5회 재시도
 * - 지수 백오프 (2초 → 4초 → 8초 → 16초 → 30초)
 */
@Configuration
@EnableAsync
@EnableRetry
class AsyncConfig : AsyncConfigurer {
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun getAsyncExecutor(): Executor {
        return ThreadPoolTaskExecutor().apply {
            corePoolSize = 2
            maxPoolSize = 5
            queueCapacity = 10
            setThreadNamePrefix("async-")
            setWaitForTasksToCompleteOnShutdown(true)
            setAwaitTerminationSeconds(60)
            setRejectedExecutionHandler { task, executor ->
                log.error("비동기 작업 거부됨: task={}, executor={}", task, executor)
                throw RejectedExecutionException("비동기 작업 큐가 가득 찼습니다")
            }
            initialize()
        }
    }

    override fun getAsyncUncaughtExceptionHandler(): AsyncUncaughtExceptionHandler {
        return AsyncUncaughtExceptionHandler { throwable, method, params ->
            log.error(
                "비동기 작업 중 예외 발생: method={}, params={}, error={}",
                method.name,
                params.joinToString(),
                throwable.message,
                throwable
            )
        }
    }
}
