package dsm.pick2024.domain.devicetoken

import dsm.pick2024.global.base.BaseUUIDEntity
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "tbl_device_token")
class DeviceTokenEntity(
    id: UUID,

    @Column(name = "device_token")
    val deviceToken: String? = null

) : BaseUUIDEntity(id)
