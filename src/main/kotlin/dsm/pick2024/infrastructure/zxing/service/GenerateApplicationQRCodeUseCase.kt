package dsm.pick2024.infrastructure.zxing.service

import java.time.LocalTime

interface GenerateApplicationQRCodeUseCase {
    fun generateApplicationQRCode(username: String, teacherName: String, startTime: LocalTime, endTime: LocalTime, reason: String)
}
