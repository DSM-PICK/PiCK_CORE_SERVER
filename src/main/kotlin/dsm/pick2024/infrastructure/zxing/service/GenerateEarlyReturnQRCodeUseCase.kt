package dsm.pick2024.infrastructure.zxing.service

import java.time.LocalTime

interface GenerateEarlyReturnQRCodeUseCase {
    fun generateEarlyReturnQRCode(username: String, teacherName: String, startTime: LocalTime, reason: String)

}
