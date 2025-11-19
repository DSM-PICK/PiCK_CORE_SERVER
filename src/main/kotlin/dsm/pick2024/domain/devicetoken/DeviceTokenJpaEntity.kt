package dsm.pick2024.domain.devicetoken

import dsm.pick2024.domain.devicetoken.enum.OSType
import dsm.pick2024.global.base.BaseUUIDEntity
import java.util.UUID
import javax.persistence.*

@Entity(name = "tbl_device_token")
class DeviceTokenJpaEntity(
    id: UUID,

    @Column(name = "user_id")
    var mailId: String,

    @Column(name = "device_token")
    val deviceToken: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val os: OSType

) : BaseUUIDEntity(id)
