package dsm.pick2024.domain.fcm.dto.request

import dsm.pick2024.domain.outbox.presentation.dto.payload.PayloadInterFace

data class FcmRequest(
    val tokens: List<String?>,
    val title: String,
    val body: String
) : PayloadInterFace()
