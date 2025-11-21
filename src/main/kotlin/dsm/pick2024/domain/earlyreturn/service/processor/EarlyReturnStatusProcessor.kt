package dsm.pick2024.domain.earlyreturn.service.processor

import dsm.pick2024.domain.application.domain.Application
import dsm.pick2024.domain.outbox.port.`in`.OutboxFacadeUseCase

abstract class EarlyReturnStatusProcessor(
    private val outboxFacadeUseCase: OutboxFacadeUseCase
) {
    abstract fun process(applications: List<Application>, adminName: String, deviceTokens: List<String>)

    protected fun sendNotification(title: String, message: String, deviceTokens: List<String>) {
        outboxFacadeUseCase.sendNotificationAll(
            title = title,
            deviceToken = deviceTokens,
            body = message
        )
    }
}
