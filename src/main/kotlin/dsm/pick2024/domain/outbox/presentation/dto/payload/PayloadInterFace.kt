package dsm.pick2024.domain.outbox.presentation.dto.payload

import com.google.gson.Gson

abstract class PayloadInterFace {
    fun toJson(): String {
        return Gson().toJson(this)
    }
}
