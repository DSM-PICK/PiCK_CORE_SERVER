package dsm.pick2024.domain.earlyreturn.service.processor

import dsm.pick2024.domain.application.domain.Application
import dsm.pick2024.domain.fcm.port.out.FcmSendPort

abstract class EarlyReturnStatusProcessor(
    private val sendMessageUseCase: FcmSendPort
) {
    abstract fun process(applications: List<Application>, adminName: String, deviceTokens: List<String>)

    protected fun sendNotification(title: String, message: String, deviceTokens: List<String>) {
        sendMessageUseCase.sendAll(deviceTokens, title, message)
    }
}
