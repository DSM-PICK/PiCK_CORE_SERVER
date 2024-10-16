package dsm.pick2024.global.config.socket.port.out

interface WebSocketHandler {
    fun sendStatusUpdate(userId: String, status: Any?)
}
