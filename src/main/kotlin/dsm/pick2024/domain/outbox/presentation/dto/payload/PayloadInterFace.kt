package dsm.pick2024.domain.outbox.presentation.dto.payload

import com.google.gson.Gson

abstract class PayloadInterFace {
    companion object {
        val gson = Gson()
    }

    fun toJson(): String {
        return gson.toJson(this)
    }
}
