package dsm.pick2024.domain.outbox.port.out

import dsm.pick2024.domain.outbox.domain.Outbox

interface SaveOutboxPort {
    fun saveOutbox(outbox: Outbox): Unit
}
