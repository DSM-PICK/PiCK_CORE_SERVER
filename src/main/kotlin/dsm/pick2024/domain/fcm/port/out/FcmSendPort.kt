package dsm.pick2024.domain.fcm.port.out

import dsm.pick2024.domain.fcm.dto.request.FcmRequest

interface FcmSendPort {
    fun send(request: FcmRequest)
}
