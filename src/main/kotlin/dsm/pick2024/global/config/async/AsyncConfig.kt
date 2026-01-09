package dsm.pick2024.global.config.async

import org.slf4j.LoggerFactory
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
import org.springframework.context.annotation.Configuration
import org.springframework.retry.annotation.EnableRetry
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor // 추가
import java.util.concurrent.Executor
import java.util.concurrent.RejectedExecutionException

@Configuration
@EnableAsync
@EnableRetry
class AsyncConfig : AsyncConfigurer {
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun getAsyncExecutor(): Executor {
        val executor = ThreadPoolTaskExecutor().apply {
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

        return DelegatingSecurityContextAsyncTaskExecutor(executor)
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
