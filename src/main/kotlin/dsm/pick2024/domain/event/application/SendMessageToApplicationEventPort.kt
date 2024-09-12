package dsm.pick2024.domain.event.application

import dsm.pick2024.domain.application.domain.Application
import dsm.pick2024.domain.application.enums.ApplicationKind
import dsm.pick2024.domain.application.enums.Status

interface SendMessageToApplicationEventPort {
    fun send(
        deviceToken: String,
        status: Status,
        applicationKind: ApplicationKind,
        application: Application?,
        isSubscribed: Boolean
    )
}
