package dsm.pick2024.infrastructure.util.port

interface RedisUtilPort {
    fun getData(key: String): String?
    fun setDataExpire(key: String, value: String, duration: Long)
    fun deleteData(key: String)
}
